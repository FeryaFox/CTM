package ru.feryafox.ctm.dto.exhibition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.feryafox.ctm.entities.Exhibition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowExhibitionDto {
    private long exhibitionId;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String theme;

    private String description;

    private BigDecimal ticketPrice;

    public static ShowExhibitionDto from(Exhibition exhibition) {
        ShowExhibitionDto dto = new ShowExhibitionDto();
        dto.exhibitionId = exhibition.getExhibitionId();
        dto.description = exhibition.getDescription();
        dto.name = exhibition.getName();
        dto.startDate = exhibition.getStartDate();
        dto.endDate = exhibition.getEndDate();
        dto.theme = exhibition.getTheme();
        dto.ticketPrice = exhibition.getTicketPrice();
        return dto;
    }
}
