package ru.feryafox.ctm.projections;

import java.math.BigDecimal;

public interface ReportExhibitionsCuratorAndTicketsProjection {
    Long getExhibitionId();
    String getName();
    String getTheme();
    Long getTicketsCount();
    BigDecimal getTicketsPrice();
    Integer getCuratorCount();
    Integer getExhibitsCount();
}
