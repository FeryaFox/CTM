package ru.feryafox.ctm.services.employee;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.feryafox.ctm.dto.RegistrationDto;
import ru.feryafox.ctm.repositories.VisitorRepository;

import java.time.LocalDateTime;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;
    private final PasswordEncoder passwordEncoder;

    public VisitorService(VisitorRepository visitorRepository, PasswordEncoder passwordEncoder) {
        this.visitorRepository = visitorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerVisitor(RegistrationDto registrationDto) {
        if (visitorRepository.findByContactPhone(registrationDto.getContactPhone()).isPresent()) {
            throw new RuntimeException("Пользователь с таким телефоном уже существует");
        }

        visitorRepository.saveVisitor(
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getMiddleName(),
                registrationDto.getGender().charAt(0),
                registrationDto.getContactPhone(),
                LocalDateTime.now(),
                0,
                passwordEncoder.encode(registrationDto.getPassword())
        );
    }
}
