package ru.feryafox.ctm.services.employee;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.dto.exhibit.ExhibitDto;
import ru.feryafox.ctm.dto.exhibit.ParticipatingExhibitDto;
import ru.feryafox.ctm.dto.exhibit.ShowExhibitDto;
import ru.feryafox.ctm.dto.exhibit.UpdateExhibitDto;
import ru.feryafox.ctm.dto.exhibition.CreateExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.ShowExhibitionDto;
import ru.feryafox.ctm.dto.exhibition.UpdateExhibitionDto;
import ru.feryafox.ctm.entities.Exhibit;
import ru.feryafox.ctm.entities.Exhibition;
import ru.feryafox.ctm.projections.ExhibitParticipationProjection;
import ru.feryafox.ctm.repositories.ExhibitParticipationRepository;
import ru.feryafox.ctm.repositories.ExhibitRepository;
import ru.feryafox.ctm.repositories.ExhibitionRepository;

import java.util.*;
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
        return exhibitionRepository.findAllExhibitionsCurrentlyAvailable().stream()
                .map(ShowExhibitionDto::from)
                .collect(Collectors.toList());
    }

    public void deleteExhibition(long exhibitionId) {
        exhibitionRepository.deleteByExhibitionId(exhibitionId);
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

    public Map<String, List<ShowExhibitDto>> getExhibitParticipationFull(Long id) {
        var projections = exhibitionRepository.findExhibitParticipation(id);

        List<ShowExhibitDto> selectedExhibits = projections.stream()
                .filter(ExhibitParticipationProjection::getIsParticipating)
                .map(p -> new ShowExhibitDto(p.getExhibitId(), p.getExhibitName(), p.getProductionDate(), p.getManufacturer(), p.getDeviceType(), p.getCondition(), p.getHistory(), p.getTechnicalSpecs()))
                .toList();

        return Map.of(
                "exhibits", selectedExhibits
        );
    }

    public Map<String, List<ExhibitDto>> getExhibitParticipation(Long id) {
        var projections = exhibitionRepository.findExhibitParticipation(id);

        List<ExhibitDto> availableExhibits = projections.stream()
                .filter(p -> !p.getIsParticipating())
                .map(p -> new ExhibitDto(p.getExhibitId(), p.getExhibitName()))
                .toList();

        List<ExhibitDto> selectedExhibits = projections.stream()
                .filter(ExhibitParticipationProjection::getIsParticipating)
                .map(p -> new ExhibitDto(p.getExhibitId(), p.getExhibitName()))
                .toList();


        return Map.of(
                "availableExhibits", availableExhibits,
                "selectedExhibits", selectedExhibits
        );
    }

    @Transactional
    public void updateExhibitionExhibits(Long exhibitionId, List<Long> exhibitIds) {

        exhibitParticipationRepository.deleteByExhibitionId(exhibitionId);

        for (Long id : exhibitIds) {
            exhibitParticipationRepository.insertExhibitParticipation(id, exhibitionId);
        }
    }
}
