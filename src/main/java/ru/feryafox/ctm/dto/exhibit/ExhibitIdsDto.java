package ru.feryafox.ctm.dto.exhibit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitIdsDto {
    private List<Long> exhibitIds;
}
