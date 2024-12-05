package ru.feryafox.ctm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Exhibition")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exhibition_id")
    private Long exhibitionId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, length = 255)
    private String theme;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    @Column
    private Integer visitorCount = 0;

    @ManyToMany
    @JoinTable(
            name = "exhibitioncurator",
            joinColumns = @JoinColumn(name = "exhibition_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> curators;

    @ManyToMany(mappedBy = "exhibitions")
    private Set<Exhibit> exhibits;
}
