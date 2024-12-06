package ru.feryafox.ctm.controllers.visitor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.feryafox.ctm.dto.exhibit.ShowExhibitDto;
import ru.feryafox.ctm.services.employee.ExhibitionService;
import ru.feryafox.ctm.services.visitor.TicketService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/visitor")
public class VisitorController {
    private final ExhibitionService exhibitionService;
    private final TicketService ticketService;

    public VisitorController(ExhibitionService exhibitionService, TicketService ticketService) {
        this.exhibitionService = exhibitionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String visitor(Model model) {
        model.addAttribute("exhibitions", exhibitionService.getExhibitions()); // TODO сделать, чтобы выводились только выставки, которые будут
        return "visitors/index";
    }

    @GetMapping("/exhibitions/{exhibitionId}/exhibits")
    @ResponseBody
    public Map<String, List<ShowExhibitDto>> exhibitions(@PathVariable("exhibitionId") Long exhibitionId) {
        return exhibitionService.getExhibitParticipationFull(exhibitionId);
    }

    @PostMapping("/ticket/{exhibitionId}")
    public String buyTicket(@PathVariable Long exhibitionId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        ticketService.buyTicket(userDetails.getUsername(), exhibitionId);
        return "redirect:/visitor/";
    }

    @GetMapping("/tickets")
    public String tickets(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;


        model.addAttribute("tickets", ticketService.getAllUserTickets(userDetails.getUsername()));
        return "visitors/ticket"; // TODO добавить возможность удалить билет
    }
}
