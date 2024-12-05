package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Exhibit;

import java.time.LocalDate;

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
}
