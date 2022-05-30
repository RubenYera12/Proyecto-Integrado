package com.ruben.rfaf.referee.application;

import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.category.infrastructure.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @Override
    public Referee findById(String id) throws Exception {
        return refereeRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el usuario"));
    }

    @Override
    public Referee findByEmail(String email) throws Exception {
        return refereeRepository
                .findByEmailIgnoreCase(email)
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
        Optional<Referee> referee1 = refereeRepository.findByEmailIgnoreCase(referee.getEmail());
        Optional<Referee> referee2 = refereeRepository.findByLicenseNum(referee.getLicenseNum());
        if (referee1.isPresent())
            throw new Exception("El usuario con email: " + referee.getEmail() + " ya existe");
        if (referee2.isPresent())
            throw new Exception("Ya existe el usuario con licencia: "+referee.getLicenseNum());
        return refereeRepository.save(referee);
    }

    @Override
    public Referee updateUser(Referee referee,String id) throws Exception {
        Referee refereeCheck = refereeRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado el arbitro"));
        if (!refereeCheck.getEmail().equals(referee.getEmail())) {
            throw new Exception("No se puede cambiar el email");
        }
        if (!refereeCheck.getLicenseNum().equals(referee.getLicenseNum()))
            throw new Exception("No se puede cambiar el numero de licencia");
        // TODO: Comprobar campos nulos
        if (referee.getTelfNumber()==null||referee.getTelfNumber().equals(""))
            referee.setTelfNumber(refereeCheck.getTelfNumber());
        if (referee.getName()==null||referee.getName().equals(""))
            referee.setName(refereeCheck.getName());
        if (referee.getFirstname()==null||referee.getFirstname().equals(""))
            referee.setFirstname(refereeCheck.getFirstname());
        if (referee.getBirthDate()==null)
            referee.setBirthDate(refereeCheck.getBirthDate());
        if (referee.getCategory()==null)
            referee.setCategory(refereeCheck.getCategory());
        if (referee.getCity()==null||referee.getCity().equals(""))
            referee.setCity(refereeCheck.getCity());
        if (referee.getImage_url()==null||referee.getImage_url().equals(""))
            referee.setImage_url(refereeCheck.getImage_url());

        return refereeRepository.save(referee);
    }

    @Override
    public List<Referee> findAll() {
        return refereeRepository.findAll();
    }

    @Override
    public List<Referee> findByCategory(String name) throws Exception {
        Category category = categoryRepository
                .findByNameIgnoreCase(name)
                .orElseThrow(()->new Exception("No existe la categoria seleccionada"));

        return refereeRepository.findByCategory_id(category.getId());
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
