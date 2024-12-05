package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Exhibition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO exhibition (name, start_date, end_date, theme, description, ticket_price) " +
            "VALUES (:name, :startDate, :endDate, :theme, :description, :ticketPrice)", nativeQuery = true)
    void saveExhibition(
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("theme") String theme,
            @Param("description") String description,
            @Param("ticketPrice") BigDecimal ticketPrice
    );

    @Transactional
    @Modifying
    @Query(value = "UPDATE exhibition SET name = :name, start_date = :startDate, end_date = :endDate, " +
            "theme = :theme, description = :description, ticket_price = :ticketPrice WHERE exhibition_id = :exhibitionId", nativeQuery = true)
    void updateExhibition(
            @Param("exhibitionId") long exhibitionId,
            @Param("name") String name,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("theme") String theme,
            @Param("description") String description,
            @Param("ticketPrice") BigDecimal ticketPrice
    );

}