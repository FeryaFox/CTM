package ru.feryafox.ctm.dto.exhibition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitionDto {
    private Long exhibitionId;
    private String name;
}
