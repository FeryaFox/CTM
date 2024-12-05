package ru.feryafox.ctm.controllers.employee;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.feryafox.ctm.dto.exhibition.CreateExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.UpdateExhibitionDto;
import ru.feryafox.ctm.services.employee.ExhibitionService;

@Controller
@RequestMapping("/employee/exhibitions")
public class ExhibitionController {
    private final ExhibitionService exhibitionService;

    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("")
    public String exhibitions(Model model) {
        model.addAttribute("exhibition", new CreateExhibitionDto());
        model.addAttribute("exhibitions", exhibitionService.getExhibitions());
        return "employee/exhibitions";
    }

    @PostMapping("")
    public String exhibitions(@Valid @ModelAttribute("exhibition") CreateExhibitionDto dto, Model model) {
        exhibitionService.addExhibition(dto);
        return "redirect:/employee/exhibitions";
    }

    @PostMapping("/delete/{id}")
    public String exhibitionDelete(@PathVariable int id) {
        exhibitionService.deleteExhibition(id);
        return "redirect:/employee/exhibitions";
    }

    @PostMapping("/update")
    public String updateExhibition(@Valid @ModelAttribute UpdateExhibitionDto dto, Model model) {
        exhibitionService.updateExhibition(dto);
        return "redirect:/employee/exhibitions";
    }
}
