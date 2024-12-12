package ru.feryafox.ctm.controllers.employee;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.feryafox.ctm.dto.employee.CreateEmployeeDto;
import ru.feryafox.ctm.dto.exhibit.ExhibitDto;
import ru.feryafox.ctm.dto.exhibit.ExhibitIdsDto;
import ru.feryafox.ctm.dto.exhibition.ExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.ExhibitionsIdsDto;
import ru.feryafox.ctm.entities.Employee;
import ru.feryafox.ctm.entities.Exhibit;
import ru.feryafox.ctm.projections.ExhibitParticipationProjection;
import ru.feryafox.ctm.services.employee.EmployeeService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public String employees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        model.addAttribute("employee", new CreateEmployeeDto());
        return "employee/employees";
    }

    @PostMapping("")
    public String addEmployee(@Valid @ModelAttribute CreateEmployeeDto employee, RedirectAttributes redirectAttributes) {
        String password = employeeService.createEmployee(employee);
        redirectAttributes.addFlashAttribute("generatedPassword", password);
        return "redirect:/employee/employees";
    }

    @GetMapping("/{id}/exhibits")
    @ResponseBody
    public Map<String, List<ExhibitDto>> exhibits(@PathVariable long id) {
        return employeeService.getExhibitCurator(id);
    }

    @GetMapping("/{id}/exhibitions")
    @ResponseBody
    public Map<String, List<ExhibitionDto>> exhibitions(@PathVariable long id) {
        return employeeService.getExhibitionCurator(id);
    }

    @PostMapping("/{employeeId}/exhibits")
    @ResponseBody
    public void updateExhibitCurator(@PathVariable Long employeeId, @RequestBody ExhibitIdsDto exhibitIdsDto) {
        employeeService.updateExhibitCurator(employeeId, exhibitIdsDto.getExhibitIds());
    }

    @PostMapping("/{employeeId}/exhibitions")
    @ResponseBody
    public void updateExhibitionCurator(@PathVariable Long employeeId, @RequestBody ExhibitionsIdsDto exhibitIdsDto) {
        employeeService.updateExhibitionCurator(employeeId, exhibitIdsDto.getExhibitionsIds());
    }
}
