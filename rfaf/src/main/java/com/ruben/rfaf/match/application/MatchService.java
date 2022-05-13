package com.ruben.rfaf.match.application;

import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.referee.domain.Referee;

import java.util.List;

public interface MatchService {
    Match createGame(Match match);

    Match findById(String id) throws Exception;

    List<Match> findAll();

    Match updateGame(Match match, String id) throws Exception;

    Match assignMatch(String match_id, String referee_id) throws Exception;

    Match cancelAssignment(String match_id) throws Exception;
}
