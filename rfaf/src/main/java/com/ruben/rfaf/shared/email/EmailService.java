package com.ruben.rfaf.shared.email;

import com.ruben.rfaf.designation.domain.Designation;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void emailDesignacionArbitro(Designation designation, String estado) throws UnsupportedEncodingException;

    void emaiConfirmacionAsistente1(Designation designation, String estado) throws UnsupportedEncodingException;

    void emaiConfirmacionAsistente2(Designation designation, String estado) throws UnsupportedEncodingException;
}
