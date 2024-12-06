package ru.feryafox.ctm.dto.exhibition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitionsIdsDto {
    private List<Long> exhibitionsIds;
}
