package ru.feryafox.ctm.controllers.employee;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.feryafox.ctm.dto.exhibit.ExhibitDto;
import ru.feryafox.ctm.dto.exhibit.ExhibitIdsDto;
import ru.feryafox.ctm.dto.exhibition.CreateExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.UpdateExhibitionDto;
import ru.feryafox.ctm.projections.ExhibitParticipationProjection;
import ru.feryafox.ctm.services.employee.ExhibitionService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}/exhibits")
    @ResponseBody
    public Map<String, List<ExhibitDto>> exhibits(@PathVariable long id) {
        return exhibitionService.getExhibitParticipation(id);
    }

    @PostMapping("/{exhibitionId}/exhibits")
    @ResponseBody
    public void updateExhibitionExhibits(@PathVariable Long exhibitionId, @RequestBody ExhibitIdsDto exhibitIdsDto) {
        exhibitionService.updateExhibitionExhibits(exhibitionId, exhibitIdsDto.getExhibitIds());
    }
}
