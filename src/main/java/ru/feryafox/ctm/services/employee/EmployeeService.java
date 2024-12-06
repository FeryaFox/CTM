package ru.feryafox.ctm.services.employee;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.components.SimplePasswordGenerator;
import ru.feryafox.ctm.dto.employee.CreateEmployeeDto;
import ru.feryafox.ctm.dto.employee.ShowEmployeeDto;
import ru.feryafox.ctm.dto.exhibit.ExhibitDto;
import ru.feryafox.ctm.dto.exhibition.ExhibitionDto;
import ru.feryafox.ctm.projections.ExhibitCuratorProjection;
import ru.feryafox.ctm.projections.ExhibitionCuratorProjection;
import ru.feryafox.ctm.repositories.EmployeeRepository;
import ru.feryafox.ctm.repositories.ExhibitCuratorRepository;
import ru.feryafox.ctm.repositories.ExhibitionCuratorRepository;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final SimplePasswordGenerator simplePasswordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final ExhibitCuratorRepository exhibitCuratorRepository;
    private final ExhibitionCuratorRepository exhibitionCuratorRepository;

    public EmployeeService(EmployeeRepository employeeRepository, SimplePasswordGenerator simplePasswordGenerator, PasswordEncoder passwordEncoder, ExhibitCuratorRepository exhibitCuratorRepository, ExhibitionCuratorRepository exhibitionCuratorRepository) {
        this.employeeRepository = employeeRepository;
        this.simplePasswordGenerator = simplePasswordGenerator;
        this.passwordEncoder = passwordEncoder;
        this.exhibitCuratorRepository = exhibitCuratorRepository;
        this.exhibitionCuratorRepository = exhibitionCuratorRepository;
    }

    public List<ShowEmployeeDto> getEmployees() {
        return employeeRepository.getAll().stream().map(ShowEmployeeDto::from).toList();
    }

    public String createEmployee(CreateEmployeeDto createEmployeeDto) {

        String password = simplePasswordGenerator.generatePassword(8);

        employeeRepository.saveEmployee(
                createEmployeeDto.getFirstName(),
                createEmployeeDto.getLastName(),
                createEmployeeDto.getMiddleName(),
                createEmployeeDto.getGender(),
                createEmployeeDto.getPosition(),
                createEmployeeDto.getBirthDate(),
                createEmployeeDto.getHireDate(),
                createEmployeeDto.getSalary(),
                createEmployeeDto.getHomeAddress(),
                createEmployeeDto.getContactPhone(),
                createEmployeeDto.getHomePhone(),
                passwordEncoder.encode(password)
        );

        return password;
    }

    public Map<String, List<ExhibitDto>> getExhibitCurator(Long id) {
        var projections = employeeRepository.findExhibitCurator(id);

        List<ExhibitDto> availableExhibits = projections.stream()
                .filter(p -> !p.getIsCuratoring())
                .map(p -> new ExhibitDto(p.getExhibitId(), p.getExhibitName()))
                .toList();

        List<ExhibitDto> selectedExhibits = projections.stream()
                .filter(ExhibitCuratorProjection::getIsCuratoring)
                .map(p -> new ExhibitDto(p.getExhibitId(), p.getExhibitName()))
                .toList();


        return Map.of(
                "availableExhibits", availableExhibits,
                "selectedExhibits", selectedExhibits
        );
    }

    public Map<String, List<ExhibitionDto>> getExhibitionCurator(Long id) {
        List<ExhibitionCuratorProjection> projections = employeeRepository.findExhibitionCurator(id);


        List<ExhibitionDto> availableExhibits = projections.stream()
                .filter(p -> !p.getIsCuratoring())
                .map(p -> new ExhibitionDto(p.getExhibitionId(), p.getExhibitionName()))
                .toList();

        List<ExhibitionDto> selectedExhibits = projections.stream()
                .filter(ExhibitionCuratorProjection::getIsCuratoring)
                .map(p -> new ExhibitionDto(p.getExhibitionId(), p.getExhibitionName()))
                .toList();

        return Map.of(
                "availableExhibits", availableExhibits,
                "selectedExhibits", selectedExhibits
        );
    }

    @Transactional
    public void updateExhibitCurator(Long curatorId, List<Long> exhibitIds) {
        System.out.println(curatorId);
        exhibitCuratorRepository.deleteExhibitByEmployeeId(curatorId);

        for (Long id : exhibitIds) {
            exhibitCuratorRepository.insertExhibitCurator(curatorId, id);
        }
    }

    @Transactional
    public void updateExhibitionCurator(Long curatorId, List<Long> exhibitionIds) {
        System.out.println(curatorId);
        exhibitionCuratorRepository.deleteExhibitionByEmployeeId(curatorId);

        for (Long id : exhibitionIds) {
            exhibitionCuratorRepository.insertExhibitionCurator(curatorId, id);
        }
    }
}
