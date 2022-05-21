package com.ruben.rfaf.designation.application;

import com.ruben.rfaf.designation.infrastructure.repository.DesignationRepository;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.match.infrastructure.repository.GameRepository;
import com.ruben.rfaf.referee.infrastructure.repository.RefereeRepository;
import com.ruben.rfaf.shared.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;
    private final GameRepository gameRepository;
    private final RefereeRepository refereeRepository;
    private final EmailService emailService;

    @Override
    public Designation findById(String id) throws Exception {
        return designationRepository.findById(id).orElseThrow(() -> new Exception("No se ha encontrado la designación"));
    }

    @Override
    public List<Designation> findAll() {
        return designationRepository.findAll();
    }

    @Override
    public void remove(String id) throws Exception {
        Designation designationCheck = designationRepository.findById(id).orElseThrow(() -> new Exception("No se ha encontrado la designación"));
        designationRepository.deleteById(id);
    }

    @Override
    public Designation assign(Designation designation) throws Exception {
        //Comprobamos que los arbitros no esten repetidos
        if (designation.getMainReferee().getEmail().equals(designation.getAssistantReferee1().getEmail()) ||
                designation.getMainReferee().getEmail().equals(designation.getAssistantReferee2().getEmail()) ||
                designation.getAssistantReferee1().getEmail().equals(designation.getAssistantReferee2().getEmail())) {
            throw new Exception("Arbitro repetido.");
        }
        //Comprobamos que esté aceptada
        if (designation.getStatus().equals("ACEPTADA"))
            throw new Exception("La designacion ya ha sido aceptada.");
        designation.setStatus("ACEPTADA");

        designation = designationRepository.save(designation);

        emailService.emailDesignacionArbitro(designation, "CONFIRMACION");
//        emailService.emaiConfirmacionAsistente1(designation, "CONFIRMACION");
//        emailService.emaiConfirmacionAsistente2(designation, "CONFIRMACION");

        return designation;
    }

    @Override
    public void cancel(Designation designation) throws Exception {
        if (designation.getStatus().equals("CANCELADA"))
            throw new Exception("La designacion ya ha sido cancelada.");
        designation.setStatus("CANCELADA");
        designationRepository.save(designation);
        emailService.emailDesignacionArbitro(designation, "CANCELACION");
//        emailService.emaiConfirmacionAsistente1(designation, "CANCELACION");
//        emailService.emaiConfirmacionAsistente2(designation, "CANCELACION");
    }

    public void update(Designation designation,String id) throws Exception {
        Designation designationCancelled = designationRepository.findById(id)
                .orElseThrow(()->new Exception("No se ha encontrado la designación: "+id));
        cancel(designationCancelled);
        assign(designation);

    }


}
