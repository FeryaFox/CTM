package ru.feryafox.ctm.dto.exhibition;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateExhibitionDto {
    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String theme;

    private String description;

    private BigDecimal ticketPrice;
}
