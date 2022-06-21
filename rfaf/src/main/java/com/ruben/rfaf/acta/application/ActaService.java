package com.ruben.rfaf.acta.application;

import com.ruben.rfaf.acta.domain.Acta;

import java.util.List;

public interface ActaService {
    Acta create(Acta acta) throws Exception;

    Acta update(Acta acta, String id) throws Exception;

    Acta findActaById(String id) throws Exception;

    List<Acta> findAll();

    List<Acta> findAllByCompetitionId(String id);
}
