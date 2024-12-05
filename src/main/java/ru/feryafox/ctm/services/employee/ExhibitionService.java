package ru.feryafox.ctm.services.employee;

import org.springframework.stereotype.Service;
import ru.feryafox.ctm.dto.exhibition.CreateExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.ShowExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.UpdateExhibitionDto;
import ru.feryafox.ctm.entities.Exhibition;
import ru.feryafox.ctm.repositories.ExhibitRepository;
import ru.feryafox.ctm.repositories.ExhibitionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;

    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    public void addExhibition(CreateExhibitionDto exhibition) {
        exhibitionRepository.saveExhibition(
                exhibition.getName(),
                exhibition.getStartDate(),
                exhibition.getEndDate(),
                exhibition.getTheme(),
                exhibition.getDescription(),
                exhibition.getTicketPrice()
        );
    }

    public List<ShowExhibitionDto> getExhibitions() {
        return exhibitionRepository.findAll().stream() // TODO переписать на SQL это
                .map(ShowExhibitionDto::from)
                .collect(Collectors.toList());
    }

    public void deleteExhibition(long exhibitionId) {
        exhibitionRepository.deleteById(exhibitionId); // TODO переписать на SQL это
    }

    public void updateExhibition( UpdateExhibitionDto exhibition) {
        exhibitionRepository.updateExhibition(
                exhibition.getExhibitionId(),
                exhibition.getName(),
                exhibition.getStartDate(),
                exhibition.getEndDate(),
                exhibition.getTheme(),
                exhibition.getDescription(),
                exhibition.getTicketPrice()
        );
    }
}
