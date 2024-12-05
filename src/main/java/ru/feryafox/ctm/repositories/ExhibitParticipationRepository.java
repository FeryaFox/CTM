package ru.feryafox.ctm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.feryafox.ctm.entities.ExhibitParticipation;

public interface ExhibitParticipationRepository extends JpaRepository<ExhibitParticipation, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ExhibitParticipation ep WHERE ep.exhibition.exhibitionId = :exhibitionId")
    void deleteByExhibitionId(@Param("exhibitionId") Long exhibitionId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ExhibitParticipation (exhibit_id, exhibition_id) VALUES (:exhibitId, :exhibitionId)", nativeQuery = true)
    void insertExhibitParticipation(@Param("exhibitId") Long exhibitId, @Param("exhibitionId") Long exhibitionId);
}

