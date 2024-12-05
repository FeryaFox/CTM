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
        return exhibitRepository.findAll();  // TODO переписать на SQL это
    }

    public void deleteExhibit(Long id) {
        exhibitRepository.deleteById(id);  // TODO переписать на SQL это
    }

    public void updateExhibit(UpdateExhibitDto exhibitDto) {
        Optional<Exhibit> optionalExhibit = exhibitRepository.findById(exhibitDto.getExhibitId()); // TODO переписать на SQL это

        if (optionalExhibit.isPresent()) {
            Exhibit exhibit = optionalExhibit.get();
            exhibit.setName(exhibitDto.getName());

            if (exhibitDto.getProductionDate() != null) {
                exhibit.setProductionDate(exhibitDto.getProductionDate());
            }
            exhibit.setManufacturer(exhibitDto.getManufacturer());
            exhibit.setDeviceType(exhibitDto.getDeviceType());
            exhibit.setCondition(exhibitDto.getCondition());
            exhibit.setHistory(exhibitDto.getHistory());
            exhibit.setTechnicalSpecs(exhibitDto.getTechnicalSpecs());
            if (exhibitDto.getMuseumEntryDate() != null) {
                exhibit.setMuseumEntryDate(exhibitDto.getMuseumEntryDate());
            }


            exhibitRepository.save(exhibit); // TODO переписать на SQL это
        } else {
            throw new IllegalArgumentException("Exhibit with ID " + exhibitDto.getExhibitId() + " not found");
        }
    }
}
