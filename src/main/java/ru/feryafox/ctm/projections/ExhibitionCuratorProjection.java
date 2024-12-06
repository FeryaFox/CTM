package ru.feryafox.ctm.projections;

public interface ExhibitionCuratorProjection {
    Long getExhibitionId();
    String getExhibitionName();
    Boolean getIsCuratoring();
}
