package ru.feryafox.ctm.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExhibitionCuratorId implements Serializable {
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "exhibition_id")
    private Long exhibitionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExhibitionCuratorId that = (ExhibitionCuratorId) o;

        if (!exhibitionId.equals(that.exhibitionId)) return false;
        return employeeId.equals(that.employeeId);
    }

    @Override
    public int hashCode() {
        int result = exhibitionId.hashCode();
        result = 31 * result + employeeId.hashCode();
        return result;
    }
}