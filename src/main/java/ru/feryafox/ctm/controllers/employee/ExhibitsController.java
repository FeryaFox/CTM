package ru.feryafox.ctm.controllers.employee;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.feryafox.ctm.dto.exhibit.CreateExhibitDto;
import ru.feryafox.ctm.dto.exhibit.UpdateExhibitDto;
import ru.feryafox.ctm.services.employee.ExhibitService;

@Controller
@RequestMapping("/employee/exhibits")
public class ExhibitsController {

    private final ExhibitService exhibitService;

    public ExhibitsController(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }

    @GetMapping("")
    public String exhibits(Model model) {
        model.addAttribute("exhibits", exhibitService.getAllExhibits());
        model.addAttribute("exhibit", new CreateExhibitDto());
        return "employee/exhibits";
    }

    @PostMapping("")
    public String addExhibit(@Valid @ModelAttribute("exhibit") CreateExhibitDto dto, Model model) {
        exhibitService.addExhibit(dto);
        model.addAttribute("exhibits", exhibitService.getAllExhibits());
        model.addAttribute("exhibit", new CreateExhibitDto());
        return "employee/exhibits";
    }

    @PostMapping("/delete/{id}")
    public String deleteExhibit(@PathVariable Long id) {
        exhibitService.deleteExhibit(id);
        return "redirect:/employee/exhibits";
    }

    @PostMapping("/update")
    public String updateExhibit(@Valid @ModelAttribute UpdateExhibitDto exhibitDto) {
        exhibitService.updateExhibit(exhibitDto);
        return "redirect:/employee/exhibits";
    }


}
