package ru.feryafox.ctm.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.feryafox.ctm.keys.ExhibitionCuratorId;

@Entity
@Table(name = "exhibitioncurator")
@Data
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
