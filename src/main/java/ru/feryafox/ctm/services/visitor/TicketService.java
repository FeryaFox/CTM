package ru.feryafox.ctm.services.visitor;

import org.springframework.stereotype.Service;
import ru.feryafox.ctm.dto.ticket.ShowTicketDto;
import ru.feryafox.ctm.entities.Exhibition;
import ru.feryafox.ctm.entities.Visitor;
import ru.feryafox.ctm.repositories.ExhibitionRepository;
import ru.feryafox.ctm.repositories.TicketRepository;
import ru.feryafox.ctm.repositories.VisitorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final VisitorRepository visitorRepository;

    public TicketService(TicketRepository ticketRepository, ExhibitionRepository exhibitionRepository, VisitorRepository visitorRepository) {
        this.ticketRepository = ticketRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.visitorRepository = visitorRepository;
    }

    public void buyTicket(String phone, Long exhibitionId) {

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId).orElseThrow(); // TODO переписать на sql
        Visitor visitor = visitorRepository.findByContactPhone(phone).orElseThrow(); // TODO переписать на sql

        ticketRepository.addTicket(
                exhibition.getTicketPrice(),
                exhibition.getExhibitionId(),
                visitor.getVisitorId(),
                LocalDateTime.now(),
                LocalDateTime.now()
        ); // TODO добавить возможность выбирать время
    }

    public List<ShowTicketDto> getAllUserTickets(String phone) {
        Visitor visitor = visitorRepository.findByContactPhone(phone).orElseThrow(); // TODO переписать на sql

        return ticketRepository.findTicketsByOwner(visitor).stream().map(p -> new ShowTicketDto(p.getTicketId(), p.getPurchaseDate(), p.getExhibitionDate(), p.getExhibition().getName(), p.getPrice())).toList();
    }
}
