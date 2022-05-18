package com.ruben.rfaf.designation.application;

import com.ruben.rfaf.designation.domain.Designation;

import java.util.List;

public interface DesignationService {
    Designation create(Designation designation);

    Designation findById(String id) throws Exception;

    List<Designation> findAll();

    void remove(String id) throws Exception;
}
