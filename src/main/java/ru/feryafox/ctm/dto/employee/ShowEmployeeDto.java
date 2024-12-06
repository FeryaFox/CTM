package ru.feryafox.ctm.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.feryafox.ctm.entities.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowEmployeeDto {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String middleName;
    private char gender;
    private String position;
    private LocalDate birthDate;
    private LocalDateTime hireDate;
    private BigDecimal salary;
    private String homeAddress;
    private String contactPhone;
    private String homePhone;

    public static ShowEmployeeDto from(Employee employee) {
        ShowEmployeeDto dto = new ShowEmployeeDto();

        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setGender(employee.getGender());
        dto.setPosition(employee.getPosition());
        dto.setBirthDate(employee.getBirthDate());
        dto.setHireDate(employee.getHireDate());
        dto.setSalary(employee.getSalary());
        dto.setHomeAddress(employee.getHomeAddress());
        dto.setContactPhone(employee.getContactPhone());
        dto.setHomePhone(employee.getHomePhone());

        return dto;
    }
}
