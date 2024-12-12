package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Exhibit;
import ru.feryafox.ctm.entities.Exhibition;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO exhibit " +
            "(name, production_date, manufacturer, device_type, condition, history, technical_specs, museum_entry_date) " +
            "VALUES (:name, :productionDate, :manufacturer, :deviceType, :condition, :history, :technicalSpecs, :museumEntryDate)",
            nativeQuery = true)
    void addExhibit(
            String name,
            LocalDate productionDate,
            String manufacturer,
            String deviceType,
            String condition,
            String history,
            String technicalSpecs,
            LocalDate museumEntryDate
    );

    @Query(value = """
        SELECT * FROM Exhibit
    """, nativeQuery = true)
    List<Exhibit> findAllExhibit();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Exhibit e WHERE e.exhibit_id = :exhibitId", nativeQuery = true)
    void deleteByExhibitId(@Param("exhibitId") Long exhibitId);

    @Query(value = "SELECT * FROM Exhibit e WHERE e.exhibit_id = :exhibitId", nativeQuery = true)
    List<Exhibit> selectByExhibitId(@Param("exhibitId") Long exhibitId);


    @Modifying
    @Transactional
    @Query(value = """
        UPDATE exhibit SET 
            name = :name, production_date = :productionDate, manufacturer = :manufacturer,
            device_type = :deviceType, condition = :condition, history = :history,
            technical_specs = :technicalSpecs, museum_entry_date = :museumEntryDate
        WHERE exhibit_id = :exhibitId
    """,
    nativeQuery = true)
    int updateExhibit(
            @Param("exhibitId") Long exhibitId,
            @Param("name") String name,
            @Param("productionDate") LocalDate productionDate,
            @Param("manufacturer") String manufacturer,
            @Param("deviceType") String deviceType,
            @Param("condition") String condition,
            @Param("history") String history,
            @Param("technicalSpecs") String technicalSpecs,
            @Param("museumEntryDate") LocalDate museumEntryDate
    );
}
