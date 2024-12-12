package ru.feryafox.ctm.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface VisitorReportProjection {

    Long getVisitorId();
    String getVisitorFullName();
    String getContactPhone();
    Integer getTotalVisits();
    Long getTicketId();
    LocalDateTime getTicketPurchaseDate();
    LocalDateTime getExhibitionVisitDate();
    Long getExhibitionId();
    String getExhibitionName();
    String getExhibitionTheme();
    LocalDateTime getExhibitionStartDate();
    LocalDateTime getExhibitionEndDate();
    BigDecimal getTicketPrice();
}
