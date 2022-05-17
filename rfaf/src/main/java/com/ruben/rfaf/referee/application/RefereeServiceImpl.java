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
    public Referee findByLicenseNum(String licenseNum) throws Exception {
        return refereeRepository
                .findByLicenseNum(licenseNum)
                .orElseThrow(() -> new Exception("No se ha podido encontrar el usuario"));
    }

    @Override
    public Referee addUser(Referee referee) throws Exception {
        Optional<Referee> referee1 = refereeRepository.findByEmail(referee.getEmail());
        Optional<Referee> referee2 = refereeRepository.findByLicenseNum(referee.getLicenseNum());
        if (referee1.isPresent())
            throw new Exception("El usuario con email: " + referee.getEmail() + " ya existe");
        if (referee2.isPresent())
            throw new Exception("Ya existe el usuario con licencia: "+referee.getLicenseNum());
        return refereeRepository.save(referee);
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
        if (!referee1.get().getLicenseNum().equals(referee.getLicenseNum()))
            throw new Exception("No se puede cambiar el numero de licencia");
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
