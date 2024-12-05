package ru.feryafox.ctm.controllers.employee;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee/")
public class EmployeeController {
    @GetMapping("")
    public String index(Model model) {
        return "redirect:/employee/exhibits";
    }
}
