package ru.feryafox.ctm.projections;

public interface ReportEmployeesExhibitionsProjection {
    Long getEmployeeId();
    String getFullName();
    String getPosition();
    Integer getExhibitionCount();
    Integer getExhibitCount();
    String getWorkExperience();
}
