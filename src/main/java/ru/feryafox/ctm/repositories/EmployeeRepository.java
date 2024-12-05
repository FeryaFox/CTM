package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM Employee WHERE contact_phone = :contactPhone", nativeQuery = true)
    Optional<Employee> findByContactPhone(@Param("contactPhone") String contactPhone);

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
}
