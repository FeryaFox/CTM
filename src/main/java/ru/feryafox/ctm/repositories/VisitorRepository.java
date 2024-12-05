package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Visitor;

import java.time.LocalDateTime;
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
}
