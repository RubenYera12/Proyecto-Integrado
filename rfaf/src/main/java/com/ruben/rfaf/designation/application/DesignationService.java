package com.ruben.rfaf.designation.application;

import com.ruben.rfaf.designation.domain.Designation;

import java.util.List;

public interface DesignationService {
    Designation assign(Designation designation) throws Exception;

    Designation findById(String id) throws Exception;

    List<Designation> findAll();

    void remove(String id) throws Exception;

    void cancel(Designation designation) throws Exception;

    Designation update(Designation designation, String id) throws Exception;
}
