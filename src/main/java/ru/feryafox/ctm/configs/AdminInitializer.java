package ru.feryafox.ctm.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.feryafox.ctm.components.SimplePasswordGenerator;
import ru.feryafox.ctm.repositories.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class AdminInitializer {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createInitialAdmin(SimplePasswordGenerator simplePasswordGenerator) {
        return args -> {
            String adminUsername = "admin";


            if (employeeRepository.findByContactPhone(adminUsername).isEmpty()) {
                String password = simplePasswordGenerator.generatePassword(16);
                employeeRepository.saveEmployee(
                        "Админ",
                        "Лис",
                        "Админович",
                        'М',
                        "Админ",
                        LocalDate.now(),
                        LocalDateTime.now(),
                        new BigDecimal("0.0"),
                        "г. Ростов-на-Дону, площадь Гагарина 1",
                        adminUsername,
                        "admin",
                        passwordEncoder.encode(password)
                );

                System.out.printf("Имя пароль админа: %s\n", password);
            }
        };
    }
}

