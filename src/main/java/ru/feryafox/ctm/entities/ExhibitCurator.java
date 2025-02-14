package ru.feryafox.ctm.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.feryafox.ctm.keys.ExhibitCuratorId;

@Entity
@Table(name = "exhibitcurator")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitCurator {
    @EmbeddedId
    private ExhibitCuratorId id;

    @ManyToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "exhibit_id", insertable = false, updatable = false)
    private Exhibit exhibit;
}
