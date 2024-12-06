package ru.feryafox.ctm.dto.ticket;

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
public class ShowTicketDto {
    private Long ticketId;
    private LocalDateTime purchaseDate;
    private LocalDateTime exhibitionDate;
    private String title;
    private BigDecimal price;
}
