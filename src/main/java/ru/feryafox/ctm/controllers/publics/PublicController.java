package ru.feryafox.ctm.controllers.publics;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.feryafox.ctm.services.employee.ExhibitionService;

@Controller
@RequestMapping("/public")
public class PublicController {
    private final ExhibitionService exhibitionService;

    public PublicController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("exhibitions", exhibitionService.getExhibitions());
        return "public/index";
    }
}
