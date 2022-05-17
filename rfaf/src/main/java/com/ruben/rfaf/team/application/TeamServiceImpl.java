package com.ruben.rfaf.team.application;

import com.ruben.rfaf.team.domain.Team;
import com.ruben.rfaf.team.infrastructure.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team addTeam(Team team) throws Exception {
        Optional<Team> teamOptional = teamRepository.findByName(team.getName());
        if (teamOptional.isPresent())
            throw new Exception("El equipo: " + team.getName() + " ya existe");
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Team team) throws Exception {
        if (team.getId() == null)
            throw new Exception("No se ha podido encontrar el equipo.");

        Optional<Team> teamOptional = teamRepository.findById(team.getId());
        if (!teamOptional.get().getName().equals(team.getName()))
            throw new Exception("No se puede cambiar el Nombre del Equipo");
        // TODO: Comprobar campos nulos
        if (team.getStadium().isEmpty())
            throw new Exception("No puedes dejar al club sin estadio");
        return teamRepository.save(team);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public String deleteById(String id) throws Exception {
        try {
            teamRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Fallo inesperado");
        }

        return "Se ha borrado correctamente al equipo";
    }

    @Override
    public Team findById(String id) throws Exception {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el equipo"));
    }

    @Override
    public Team findByName(String name) throws Exception {
        Optional<Team> team = Optional.ofNullable(teamRepository.findByName(name).orElseThrow(() -> new Exception("No se ha podido encontrar el equipo")));
        return team.get();
    }
}
