package ru.feryafox.ctm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.feryafox.ctm.keys.ExhibitParticipationId;

@Entity
@Table(name = "exhibitparticipation")
@Data
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

