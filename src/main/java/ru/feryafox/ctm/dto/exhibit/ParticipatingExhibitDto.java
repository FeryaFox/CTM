package ru.feryafox.ctm.dto.exhibit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipatingExhibitDto {
    private long exhibit_id;

    private String exhibit_name;

    private boolean is_participating;
}