package ru.feryafox.ctm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.*;
import ru.feryafox.ctm.keys.ExhibitParticipationId;

@Entity
@Table(name = "exhibitparticipation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitParticipation {

    @EmbeddedId
    private ExhibitParticipationId id;

    @ManyToOne
    @JoinColumn(name = "exhibit_id", insertable = false, updatable = false)
    private Exhibit exhibit;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", insertable = false, updatable = false)
    private Exhibition exhibition;
}

