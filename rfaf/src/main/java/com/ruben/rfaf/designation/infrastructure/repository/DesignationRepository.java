package com.ruben.rfaf.designation.infrastructure.repository;

import com.ruben.rfaf.designation.domain.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation,String> {

}
