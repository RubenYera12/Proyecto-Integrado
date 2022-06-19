package com.ruben.rfaf.goal.infraestructure;

import com.ruben.rfaf.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Integer> {
}
