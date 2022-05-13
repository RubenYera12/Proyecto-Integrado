package com.ruben.rfaf.competition.infrastructure.repository;

import com.ruben.rfaf.competition.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition,String> {
}
