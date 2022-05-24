package com.ruben.rfaf.team.infrastructure.repository;

import com.ruben.rfaf.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,String> {
    Optional<Team> findByNameIgnoreCase(String name);
}
