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
public class ExhibitCuratorId implements Serializable {
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "exhibit_id")
    private Long exhibitId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExhibitCuratorId that = (ExhibitCuratorId) o;

        if (!exhibitId.equals(that.exhibitId)) return false;
        return employeeId.equals(that.employeeId);
    }

    @Override
    public int hashCode() {
        int result = exhibitId.hashCode();
        result = 31 * result + employeeId.hashCode();
        return result;
    }
}
