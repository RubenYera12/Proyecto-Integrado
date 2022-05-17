package com.ruben.rfaf.competition.application;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.competition.infrastructure.repository.CompetitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private CompetitionRepository competitionRepository;

    @Override
    public Competition createCompetition(Competition competition) throws Exception {
        Optional<Competition> competitionCheck = competitionRepository.findByName(competition.getName());
        if (competitionCheck.isPresent())
            throw new Exception("Ya existe la Competición: " + competition.getName());
        return competitionRepository.save(competition);
    }

    @Override
    public Competition findById(String id) throws Exception {
        return competitionRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado una competicion con ID: " + id));
    }

    @Override
    public Competition findByName(String name) throws Exception {
        return competitionRepository
                .findByName(name)
                .orElseThrow(() -> new Exception("No se ha encontrado la competicion: " + name));
    }

    @Override
    public List<Competition> findAll() {
        return competitionRepository
                .findAll();
    }

    @Override
    public void deleteById(String id) throws Exception {
        Competition competition = findById(id);
        competitionRepository.delete(competition);
    }

    @Override
    public Competition update(Competition competition, String id) throws Exception {
        Competition competitionCheck = findById(id);
        if (competition.getName()!=null)
            competitionCheck.setName(competition.getName());
        if(competition.getZone()!=null)
            competitionCheck.setZone(competition.getZone());
        return competitionRepository.save(competitionCheck);

    }
}
