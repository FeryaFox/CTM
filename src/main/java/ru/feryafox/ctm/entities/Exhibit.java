package ru.feryafox.ctm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Exhibit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exhibit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibit_id")
    private Long exhibitId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate productionDate;

    @Column(length = 255)
    private String manufacturer;

    @Column(length = 100)
    private String deviceType;

    @Column(nullable = false, length = 255)
    private String condition;

    private String history;

    private String technicalSpecs;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate museumEntryDate;

    @ManyToMany
    @JoinTable(
            name = "exhibitparticipation",
            joinColumns = @JoinColumn(name = "exhibit_id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id")
    )
    private Set<Exhibition> exhibitions;

    @ManyToMany
    @JoinTable(
            name = "exhibitcurator",
            joinColumns = @JoinColumn(name = "exhibit_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> curators;
}
