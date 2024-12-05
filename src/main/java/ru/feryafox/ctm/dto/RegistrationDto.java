package ru.feryafox.ctm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {
    @NotEmpty(message = "Имя не может быть пустым")
    private String firstName;

    @NotEmpty(message = "Фамилия не может быть пустым")
    private String lastName;

    private String middleName;

    @Pattern(regexp = "М|Ж", message = "Пол должен быть 'м' или 'ж'")
    private String gender;

    @NotEmpty(message = "Телефон не может быть пустым")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Некорректный номер телефона")
    private String contactPhone;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;
}
