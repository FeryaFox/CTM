package ru.feryafox.ctm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.feryafox.ctm.keys.ExhibitCuratorId;

@Entity
@Table(name = "exhibitcurator")
@Data
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
