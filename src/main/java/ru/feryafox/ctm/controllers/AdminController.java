package ru.feryafox.ctm.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.feryafox.ctm.repositories.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class AdminController {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("create")
    public void create() {
        employeeRepository.saveEmployee(
                "Имя",
                "Фамилия",
                "Отчество",
                'М',
                "Начальник",
                LocalDate.now(),
                LocalDateTime.now(),
                new BigDecimal("1000.0"),
                "г. Ростов-на-Дону, площадь Гагарина 1",
                "+79286273164",
                "2383434",
                passwordEncoder.encode("12345678")
        );
    }
}
