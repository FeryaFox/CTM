package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.ExhibitionCurator;

public interface ExhibitionCuratorRepository extends JpaRepository<ExhibitionCurator, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ExhibitionCurator ep WHERE ep.employee.employeeId = :employeeId")
    void deleteExhibitionByEmployeeId(@Param("employeeId") Long employeeId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ExhibitionCurator (employee_id, exhibition_id) VALUES (:employeeId, :exhibitionId)", nativeQuery = true)
    void insertExhibitionCurator(@Param("employeeId") Long employeeId, @Param("exhibitionId") Long exhibitionId);

}
