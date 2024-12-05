package ru.feryafox.ctm.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExhibitParticipationId implements Serializable {

    @Column(name = "exhibit_id")
    private Long exhibitId;

    @Column(name = "exhibition_id")
    private Long exhibitionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExhibitParticipationId that = (ExhibitParticipationId) o;

        if (!exhibitId.equals(that.exhibitId)) return false;
        return exhibitionId.equals(that.exhibitionId);
    }

    @Override
    public int hashCode() {
        int result = exhibitId.hashCode();
        result = 31 * result + exhibitionId.hashCode();
        return result;
    }
}

