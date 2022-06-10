package com.ruben.rfaf.match.application;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.competition.infrastructure.repository.CompetitionRepository;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.designation.infrastructure.repository.DesignationRepository;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.match.infrastructure.repository.GameRepository;
import com.ruben.rfaf.team.domain.Team;
import com.ruben.rfaf.team.infrastructure.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final GameRepository gameRepository;
    private final CompetitionRepository competitionRepository;
    private final TeamRepository teamRepository;
    private final DesignationRepository designationRepository;

    @Override
    public Match createGame(Match match) throws Exception {
        Competition competition = competitionRepository
                .findById(match.getCompetition().getId())
                .orElseThrow(()->new Exception("No se ha encontrado la competicion"));
        match.setCompetition(competition);
        Team team = teamRepository
                .findById(match.getLocal().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el equipo local"));
        match.setLocal(team);
        Team team1 = teamRepository
                .findById(match.getVisitor().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el equipo visitante"));
        match.setVisitor(team1);
        return gameRepository.save(match);
    }

    @Override
    public Match findById(String id) throws Exception {
        return gameRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No existe un partido con ID: " + id));
    }

    @Override
    public List<Match> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Match updateGame(Match match, String id) throws Exception {
        Match matchToChange = gameRepository.findById(id).orElseThrow(()-> new Exception("No se ha encontrado el partido."));
        return gameRepository.save(match);
    }

    @Override
    public String delete(String id) throws Exception {
        Match match = gameRepository.findById(id).orElseThrow(()-> new Exception("No se ha encontrado el partido"));
        Optional<Designation> designation = designationRepository.findByMatchId(id);
        if(designation.isPresent())
            throw new Exception("No puedes borrar el partido, tiene una designación asociada");
        gameRepository.delete(match);
        return "Se ha borrado correctamente";
    }
}
