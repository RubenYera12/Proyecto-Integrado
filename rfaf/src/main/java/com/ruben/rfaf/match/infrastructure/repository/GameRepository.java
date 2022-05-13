package com.ruben.rfaf.match.infrastructure.repository;

import com.ruben.rfaf.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Match,String> {
}
