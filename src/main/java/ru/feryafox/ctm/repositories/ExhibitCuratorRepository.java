package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.ExhibitCurator;

public interface ExhibitCuratorRepository extends JpaRepository<ExhibitCurator, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ExhibitCurator ep WHERE ep.employee.employeeId = :employeeId")
    void deleteExhibitByEmployeeId(@Param("employeeId") Long employeeId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ExhibitCurator (employee_id, exhibit_id) VALUES (:employeeId, :exhibitId)", nativeQuery = true)
    void insertExhibitCurator(@Param("employeeId") Long employeeId, @Param("exhibitId") Long exhibitId);

}
