package ru.feryafox.ctm.projections;

import java.time.LocalDate;

public interface ExhibitParticipationProjection {
    Long getExhibitId();
    String getExhibitName();
    LocalDate getProductionDate();
    String getManufacturer();
    String getDeviceType();
    String getCondition();
    String getHistory();
    String getTechnicalSpecs();
    Boolean getIsParticipating();
}