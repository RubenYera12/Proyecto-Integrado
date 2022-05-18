package com.ruben.rfaf.designation.application;

import com.ruben.rfaf.designation.infrastructure.repository.DesignationRepository;
import com.ruben.rfaf.designation.domain.Designation;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DesignationServiceImpl implements DesignationService{

    private final DesignationRepository designationRepository;

    @Override
    public Designation create(Designation designation){
        return designationRepository.save(designation);
    }

    @Override
    public Designation findById(String id) throws Exception {
        return designationRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado la designación"));
    }

    @Override
    public List<Designation> findAll(){
        return designationRepository.findAll();
    }

    @Override
    public void remove(String id) throws Exception {
        Designation designationCheck = designationRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado la designación"));
        designationRepository.deleteById(did);
    }

}
