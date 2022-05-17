package com.ruben.rfaf.competition.infrastructure.repository;

import com.ruben.rfaf.competition.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition,String> {
    Optional<Competition> findByName(String name);
}
