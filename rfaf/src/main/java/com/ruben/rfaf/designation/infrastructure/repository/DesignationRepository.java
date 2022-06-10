package com.ruben.rfaf.designation.infrastructure.repository;

import com.ruben.rfaf.designation.domain.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignationRepository extends JpaRepository<Designation,String> {

    Optional<Designation> findByMatchId(String MatchId);
}
