package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.dto.exhibit.ParticipatingExhibitDto;
import ru.feryafox.ctm.entities.Exhibition;
import ru.feryafox.ctm.projections.ExhibitParticipationProjection;
import ru.feryafox.ctm.projections.ReportExhibitionsCuratorAndTicketsProjection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Query(value = """
        SELECT
            ex.exhibit_id,
            ex.name AS exhibit_name,
            ex.production_date,
            ex.manufacturer,
            ex.device_type,
            ex.condition,
            ex.history,
            ex.technical_specs,
            CASE WHEN ep.exhibit_id IS NOT NULL THEN TRUE ELSE FALSE END AS is_participating
        FROM
            Exhibition e
                CROSS JOIN
                Exhibit ex
                LEFT JOIN
                exhibitparticipation ep ON e.exhibition_id = ep.exhibition_id AND ex.exhibit_id = ep.exhibit_id
        WHERE e.exhibition_id = :exhibitionId
    """, nativeQuery = true)
    List<ExhibitParticipationProjection> findExhibitParticipation(@Param("exhibitionId") long exhibitionId);

    @Query(value = """
        SELECT * FROM Exhibition
    """, nativeQuery = true)
    List<Exhibition> findAllExhibitions();

    @Query(value = """
    SELECT * FROM Exhibition
    WHERE start_date <= CURRENT_TIMESTAMP AND end_date >= CURRENT_TIMESTAMP
""", nativeQuery = true)
    List<Exhibition> findAllExhibitionsCurrentlyAvailable();


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Exhibition e WHERE e.exhibition_id = :exhibitionId", nativeQuery = true)
    void deleteByExhibitionId(@Param("exhibitionId") Long exhibitionId);

    @Query(value = """
        SELECT
            e.exhibition_id AS "exhibition_id",
            e.name AS "name",
            e.theme AS "theme",
            COUNT(DISTINCT t.ticket_id) AS "tickets_count",
            SUM(t.price) AS "tickets_price",
            COUNT(DISTINCT ec.employee_id) AS "curator_count",
            COUNT(DISTINCT ep.exhibit_id) AS "exhibits_count"
        FROM
            Exhibition e
                LEFT JOIN Ticket t ON e.exhibition_id = t.exhibition_id
                LEFT JOIN ExhibitionCurator ec ON e.exhibition_id = ec.exhibition_id
                LEFT JOIN ExhibitParticipation ep ON e.exhibition_id = ep.exhibition_id
        WHERE
            t.purchase_date BETWEEN :start_date AND :end_date
        GROUP BY
            e.exhibition_id, e.name, e.theme
        ORDER BY
            SUM(t.price) DESC;
        
    """, nativeQuery = true)
    List<ReportExhibitionsCuratorAndTicketsProjection> getReportExhibitionsCuratorAndTickets(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate
    );
}