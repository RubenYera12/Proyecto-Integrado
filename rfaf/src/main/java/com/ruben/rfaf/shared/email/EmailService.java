package com.ruben.rfaf.shared.email;

import com.ruben.rfaf.designation.domain.Designation;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void emailDesignacionArbitro(Designation designation, String estado) throws UnsupportedEncodingException, MessagingException;

    void emailDesignacionAsistente1(Designation designation, String estado) throws UnsupportedEncodingException, MessagingException;

    void emailDesignacionAsistente2(Designation designation, String estado) throws UnsupportedEncodingException, MessagingException;
}
