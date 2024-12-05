package ru.feryafox.ctm.dto.exhibit;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhibitDto {
    private Long exhibitId;
    private String name;

}