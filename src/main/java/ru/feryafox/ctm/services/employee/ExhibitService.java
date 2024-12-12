package ru.feryafox.ctm.services.employee;

import org.springframework.stereotype.Service;
import ru.feryafox.ctm.dto.exhibit.CreateExhibitDto;
import ru.feryafox.ctm.dto.exhibit.UpdateExhibitDto;
import ru.feryafox.ctm.entities.Exhibit;
import ru.feryafox.ctm.repositories.ExhibitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExhibitService {
    private final ExhibitRepository exhibitRepository;

    public ExhibitService(ExhibitRepository exhibitRepository) {
        this.exhibitRepository = exhibitRepository;
    }

    public void addExhibit(CreateExhibitDto exhibit) {
        exhibitRepository.addExhibit(
                exhibit.getName(),
                exhibit.getProductionDate(),
                exhibit.getManufacturer(),
                exhibit.getDeviceType(),
                exhibit.getCondition(),
                exhibit.getHistory(),
                exhibit.getTechnicalSpecs(),
                exhibit.getMuseumEntryDate()
        );
    }

    public List<Exhibit> getAllExhibits() {
        return exhibitRepository.findAll();
    }

    public void deleteExhibit(Long id) {
        exhibitRepository.deleteById(id);
    }

    public void updateExhibit(UpdateExhibitDto exhibitDto) {
        int updatedRows = exhibitRepository.updateExhibit(
                exhibitDto.getExhibitId(),
                exhibitDto.getName(),
                exhibitDto.getProductionDate(),
                exhibitDto.getManufacturer(),
                exhibitDto.getDeviceType(),
                exhibitDto.getCondition(),
                exhibitDto.getHistory(),
                exhibitDto.getTechnicalSpecs(),
                exhibitDto.getMuseumEntryDate()
        );

        if (updatedRows == 0) {
            throw new IllegalArgumentException("Exhibit with ID " + exhibitDto.getExhibitId() + " not found");
        }
    }
}
