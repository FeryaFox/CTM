package ru.feryafox.ctm.projections;

public interface ExhibitParticipationProjection {
    Long getExhibitId();
    String getExhibitName();
    Boolean getIsParticipating();
}