package com.ruben.rfaf.acta.infraestructure.repository;

import com.ruben.rfaf.acta.domain.Acta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActaRepository extends JpaRepository<Acta,String> {
}
