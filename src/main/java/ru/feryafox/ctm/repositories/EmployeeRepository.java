package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Employee;
import ru.feryafox.ctm.projections.ExhibitCuratorProjection;
import ru.feryafox.ctm.projections.ExhibitParticipationProjection;
import ru.feryafox.ctm.projections.ExhibitionCuratorProjection;
import ru.feryafox.ctm.projections.ReportEmployeesExhibitionsProjection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM Employee WHERE contact_phone = :contactPhone", nativeQuery = true)
    Optional<Employee> findByContactPhone(@Param("contactPhone") String contactPhone);

    @Query(value = "SELECT * FROM Employee", nativeQuery = true)
    List<Employee> getAll();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO employee (first_name, last_name, middle_name, gender, position, birth_date, hire_date, salary, home_address, contact_phone, home_phone, password_hash) " +
            "VALUES (:firstName, :lastName, :middleName, :gender, :position, :birthDate, :hireDate, :salary, :homeAddress, :contactPhone, :homePhone, :passwordHash)", nativeQuery = true)
    void saveEmployee(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("gender") char gender,
            @Param("position") String position,
            @Param("birthDate") LocalDate birthDate,
            @Param("hireDate") LocalDateTime hireDate,
            @Param("salary") BigDecimal salary,
            @Param("homeAddress") String homeAddress,
            @Param("contactPhone") String contactPhone,
            @Param("homePhone") String homePhone,
            @Param("passwordHash") String passwordHash
    );
    @Query(value = """
        SELECT
            ex.exhibit_id,
            ex.name AS exhibit_name,
            CASE WHEN ep.exhibit_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_curatoring
        FROM
            employee e
                CROSS JOIN
                Exhibit ex
                LEFT JOIN
                exhibitcurator ep ON e.employee_id = ep.employee_id AND ex.exhibit_id = ep.exhibit_id
        WHERE e.employee_id = :employeeId
    """, nativeQuery = true)
    List<ExhibitCuratorProjection> findExhibitCurator(@Param("employeeId") long employeeId);

    @Query(value = """
        SELECT
            ex.exhibition_id,
            ex.name AS exhibition_name,
            CASE WHEN ep.exhibition_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_curatoring
        FROM
            employee e
                CROSS JOIN
                exhibition ex
                LEFT JOIN
                exhibitioncurator ep ON e.employee_id = ep.employee_id AND ex.exhibition_id = ep.exhibition_id
        WHERE e.employee_id = :employeeId
    """, nativeQuery = true)
    List<ExhibitionCuratorProjection> findExhibitionCurator(@Param("employeeId") long employeeId);

    @Query(value = """

        SELECT
            emp.employee_id AS "employee_id",
            CONCAT(emp.last_name, ' ', emp.first_name, ' ', COALESCE(emp.middle_name, '')) AS "full_name",
            emp.position AS "position",
            COUNT(DISTINCT ec.exhibition_id) AS "exhibition_count",
            COUNT(DISTINCT ex_cur.exhibit_id) AS "exhibit_count",
            CASE
                WHEN EXTRACT(YEAR FROM AGE(NOW(), emp.hire_date)) > 0 AND EXTRACT(MONTH FROM AGE(NOW(), emp.hire_date)) > 0 THEN
                    CONCAT(EXTRACT(YEAR FROM AGE(NOW(), emp.hire_date)), ' лет и ', EXTRACT(MONTH FROM AGE(NOW(), emp.hire_date)), ' месяцев')
                WHEN EXTRACT(YEAR FROM AGE(NOW(), emp.hire_date)) > 0 THEN
                    CONCAT(EXTRACT(YEAR FROM AGE(NOW(), emp.hire_date)), ' лет')
                ELSE
                    CONCAT(EXTRACT(MONTH FROM AGE(NOW(), emp.hire_date)), ' месяцев')
            END AS "work_experience"
        FROM
            Employee emp
                LEFT JOIN ExhibitionCurator ec ON emp.employee_id = ec.employee_id
                LEFT JOIN Exhibition e ON ec.exhibition_id = e.exhibition_id
                LEFT JOIN ExhibitCurator ex_cur ON emp.employee_id = ex_cur.employee_id
                LEFT JOIN Exhibit ex ON ex_cur.exhibit_id = ex.exhibit_id
        WHERE
            e.start_date BETWEEN :start_date AND :end_date
        GROUP BY
            emp.employee_id, emp.last_name, emp.first_name, emp.middle_name, emp.position, emp.hire_date
        ORDER BY
            COUNT(DISTINCT ec.exhibition_id) DESC,
            COUNT(DISTINCT ex_cur.exhibit_id) DESC;
        
        """, nativeQuery = true)
    List<ReportEmployeesExhibitionsProjection> getReportEmployeesExhibitions(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate
    );
}
