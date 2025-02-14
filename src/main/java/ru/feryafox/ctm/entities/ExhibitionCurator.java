package ru.feryafox.ctm.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.feryafox.ctm.keys.ExhibitionCuratorId;

@Entity
@Table(name = "exhibitioncurator")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitionCurator {
    @EmbeddedId
    private ExhibitionCuratorId id;

    @ManyToOne
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", insertable = false, updatable = false)
    private Exhibition exhibition;
}
