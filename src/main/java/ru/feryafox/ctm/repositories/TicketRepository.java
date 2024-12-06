package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Ticket;
import ru.feryafox.ctm.entities.Visitor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Ticket (price, exhibition_id, owner_id, purchase_date, exhibition_date) " +
            "VALUES (:price, :exhibitionId, :ownerId, :purchaseDate, :exhibitionDate)", nativeQuery = true)
    int addTicket(BigDecimal price, Long exhibitionId, Long ownerId, LocalDateTime purchaseDate, LocalDateTime exhibitionDate);

    @Query("SELECT t FROM Ticket t JOIN t.exhibition e")
    List<Ticket> findAllTicketsWithExhibitionName();

    @Query("SELECT t FROM Ticket t JOIN t.exhibition e WHERE t.owner = :owner")
    List<Ticket> findTicketsByOwner(Visitor owner);
}
