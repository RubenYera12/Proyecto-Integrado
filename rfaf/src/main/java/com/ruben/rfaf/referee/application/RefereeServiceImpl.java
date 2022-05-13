package com.ruben.rfaf.referee.application;

import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.repository.RefereeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RefereeServiceImpl implements RefereeService {

    private final RefereeRepository refereeRepository;

    @Override
    public Referee findById(String id) throws Exception {
        return refereeRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el usuario"));
    }

    @Override
    public Referee findByEmail(String email) throws Exception {
        return refereeRepository
                .findByEmail(email)
                .orElseThrow(() -> new Exception("No se ha podido encontrar el usuario"));
    }

    @Override
    public Referee addUser(Referee referee) throws Exception {
        Optional<Referee> referee1 = refereeRepository.findByEmail(referee.getEmail());
        if (referee1.isEmpty()) {
            return refereeRepository.save(referee);
        } else throw new Exception("El usuario con email: " + referee.getEmail() + " ya existe");
        // TODO: comprobar id existente
    }

    @Override
    public Referee updateUser(Referee referee) throws Exception {
        if (referee.getId() == null) {
            throw new Exception("No se ha podido encontrar el usuario.");
        }
        Optional<Referee> referee1 = refereeRepository.findById(referee.getId());
        if (!referee1.get().getEmail().equals(referee.getEmail())) {
            throw new Exception("No se puede cambiar el email");
        }
        // TODO: Comprobar campos nulos

        return refereeRepository.save(referee);
    }

    @Override
    public List<Referee> findAll() {
        return refereeRepository.findAll();
    }

    @Override
    public String deleteById(String id) throws Exception {
        try {
            refereeRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Fallo inesperado");
        }

        return "Se ha borrado correctamente al usuario";
    }
}
