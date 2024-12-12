package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Visitor;
import ru.feryafox.ctm.projections.VisitorReportProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

    @Query(value = "SELECT * FROM Visitor WHERE contact_phone = :contactPhone", nativeQuery = true)
    Optional<Visitor> findByContactPhone(@Param("contactPhone") String contactPhone);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Visitor (first_name, last_name, middle_name, gender, contact_phone, last_visit_date, visit_count, password_hash) " +
            "VALUES (:firstName, :lastName, :middleName, :gender, :contactPhone, :lastVisitDate, :visitCount, :passwordHash)", nativeQuery = true)
    void saveVisitor(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("gender") char gender,
            @Param("contactPhone") String contactPhone,
            @Param("lastVisitDate") LocalDateTime lastVisitDate,
            @Param("visitCount") Integer visitCount,
            @Param("passwordHash") String passwordHash
    );


    @Query(value = """
    SELECT 
        v.visitor_id AS visitorId, 
        CONCAT(v.last_name, ' ', v.first_name, ' ', COALESCE(v.middle_name, '')) AS visitorFullName,
        v.contact_phone AS contactPhone,
        v.visit_count AS totalVisits,
        t.ticket_id AS ticketId,
        t.purchase_date AS ticketPurchaseDate,
        t.exhibition_date AS exhibitionVisitDate,
        e.exhibition_id AS exhibitionId,
        e.name AS exhibitionName,
        e.theme AS exhibitionTheme,
        e.start_date AS exhibitionStartDate,
        e.end_date AS exhibitionEndDate,
        e.ticket_price AS ticketPrice
    FROM 
        Visitor v
    LEFT JOIN 
        Ticket t ON v.visitor_id = t.owner_id
    LEFT JOIN 
        Exhibition e ON t.exhibition_id = e.exhibition_id
    WHERE 
        t.exhibition_date BETWEEN :startDate AND :endDate
        AND v.contact_phone = :contactPhone  -- Фильтр по ID посетителя
    ORDER BY 
        t.purchase_date
    """, nativeQuery = true)
    List<VisitorReportProjection> getVisitorReportByVisitorId(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("contactPhone") String contactPhone
    );


}
