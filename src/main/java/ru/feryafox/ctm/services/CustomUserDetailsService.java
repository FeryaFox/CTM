package ru.feryafox.ctm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.Employee;
import ru.feryafox.ctm.entities.Visitor;
import ru.feryafox.ctm.repositories.EmployeeRepository;
import ru.feryafox.ctm.repositories.VisitorRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        Optional<Visitor> user = visitorRepository.findByContactPhone(phoneNumber);

        if (user.isEmpty()) {
            Optional<Employee> employee = employeeRepository.findByContactPhone(phoneNumber);

            if (employee.isPresent()) return employee.get();
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user.get();
    }
}