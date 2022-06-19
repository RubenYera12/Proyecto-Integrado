package com.ruben.rfaf.redCard.infraestructure;

import com.ruben.rfaf.redCard.domain.RedCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedCardRepository extends JpaRepository<RedCard,Integer> {
}
