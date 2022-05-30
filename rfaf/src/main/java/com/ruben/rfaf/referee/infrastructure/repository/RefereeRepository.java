package com.ruben.rfaf.referee.infrastructure.repository;

import com.ruben.rfaf.referee.domain.Referee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefereeRepository extends JpaRepository<Referee, String> {
    Optional<Referee> findByEmailIgnoreCase(String email);

    Optional<Referee> findByLicenseNum(String licenseNum);

    List<Referee> findByCategory_id(String category_id);
}
