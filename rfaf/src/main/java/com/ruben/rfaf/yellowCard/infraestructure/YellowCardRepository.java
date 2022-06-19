package com.ruben.rfaf.yellowCard.infraestructure;

import com.ruben.rfaf.yellowCard.domain.YellowCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YellowCardRepository extends JpaRepository<YellowCard,Integer> {
}
