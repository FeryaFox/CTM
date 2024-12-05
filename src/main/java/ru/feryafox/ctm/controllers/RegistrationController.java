package ru.feryafox.ctm.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.feryafox.ctm.dto.RegistrationDto;
import ru.feryafox.ctm.services.employee.VisitorService;


@Controller
public class RegistrationController {

    private final VisitorService visitorService;

    public RegistrationController( VisitorService visitorService ) {
        this.visitorService = visitorService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid RegistrationDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }

        visitorService.registerVisitor(userDto);

        return "redirect:/login?success";
    }
}
