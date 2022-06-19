package com.ruben.rfaf.acta.application;

import com.ruben.rfaf.acta.domain.Acta;

public interface ActaService {
    Acta create(Acta acta) throws Exception;

    Acta update(Acta acta, String id) throws Exception;

    Acta findActaById(String id) throws Exception;
}
