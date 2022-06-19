package com.ruben.rfaf.match.infrastructure.repository;

import com.ruben.rfaf.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match,String> {
    @Query("Select m from Match m where LOCAL_ID like ?1 or VISITOR_ID like ?1")
    List<Match> findByTeamId(String teamId);

    List<Match> findByIdNotIn(List<String> ids);
}
