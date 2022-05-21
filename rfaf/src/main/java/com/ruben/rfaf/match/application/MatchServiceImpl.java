package com.ruben.rfaf.match.application;

import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.match.infrastructure.repository.GameRepository;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.repository.RefereeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final GameRepository gameRepository;
    private final RefereeRepository refereeRepository;

    @Override
    public Match createGame(Match match) {
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
}
