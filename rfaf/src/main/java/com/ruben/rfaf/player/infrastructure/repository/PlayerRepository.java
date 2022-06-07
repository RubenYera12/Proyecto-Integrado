package com.ruben.rfaf.player.infrastructure.repository;

import com.ruben.rfaf.player.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,String> {
    Optional<Player> findByLicenseNum(String licenseNum);
    List<Player> findByTeamId(String teamid);
}
