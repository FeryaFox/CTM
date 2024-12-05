package ru.feryafox.ctm.dto.exhibit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateExhibitDto {
    private long exhibitId;

    private String name;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate productionDate;

    private String manufacturer;

    private String deviceType;

    private String condition;

    private String history;

    private String technicalSpecs;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate museumEntryDate;
}
