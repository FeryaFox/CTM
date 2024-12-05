package ru.feryafox.ctm.services.employee;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.dto.exhibit.ParticipatingExhibitDto;
import ru.feryafox.ctm.dto.exhibition.CreateExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.ShowExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.UpdateExhibitionDto;
import ru.feryafox.ctm.entities.Exhibit;
import ru.feryafox.ctm.entities.Exhibition;
import ru.feryafox.ctm.projections.ExhibitParticipationProjection;
import ru.feryafox.ctm.repositories.ExhibitParticipationRepository;
import ru.feryafox.ctm.repositories.ExhibitRepository;
import ru.feryafox.ctm.repositories.ExhibitionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitRepository exhibitRepository;
    private final ExhibitParticipationRepository exhibitParticipationRepository;

    public ExhibitionService(ExhibitionRepository exhibitionRepository, ExhibitRepository exhibitRepository, ExhibitParticipationRepository exhibitParticipationRepository) {
        this.exhibitionRepository = exhibitionRepository;
        this.exhibitRepository = exhibitRepository;
        this.exhibitParticipationRepository = exhibitParticipationRepository;
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

    public List<ExhibitParticipationProjection> getExhibitParticipation(Long id) {
        return exhibitionRepository.findExhibitParticipation(id);
    }

    @Transactional
    public void updateExhibitionExhibits(Long exhibitionId, List<Long> exhibitIds) {

        exhibitParticipationRepository.deleteByExhibitionId(exhibitionId);

        for (Long id : exhibitIds) {
            exhibitParticipationRepository.insertExhibitParticipation(id, exhibitionId);
        }
    }
}
