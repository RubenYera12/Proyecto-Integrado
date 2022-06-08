package com.ruben.rfaf.player.application;

import com.ruben.rfaf.player.domain.Player;

import java.util.List;

public interface PlayerService {
    Player addPlayer(Player player) throws Exception;
    Player updatePlayer(Player player,String id) throws Exception;
    List<Player> findAll();
    String deleteById(String id) throws Exception;
    Player findById(String id) throws Exception;
    Player findByLicense(String license) throws Exception;
    List<Player> findByTeam(String name) throws  Exception;
    List<Player> findNoTeamPlayers();
}
