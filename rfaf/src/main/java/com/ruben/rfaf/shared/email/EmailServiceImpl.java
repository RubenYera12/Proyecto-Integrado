package com.ruben.rfaf.shared.email;

import com.ruben.rfaf.designation.domain.Designation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    public void emailDesignacionArbitro(Designation designation, String estado) throws UnsupportedEncodingException {
        if (estado.equals("CONFIRMACION")) {
            estado = "Le ha sido asignada la siguiente designación:";
        } else {
            estado = "Su designación ha sido cancelada:";
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Comité Árbitros")));
        message.setTo(designation.getMainReferee().getEmail());
        message.setSubject("Aviso desde el Comité de Árbitros.");
        String contenido = estado + "\n \n" +
                "Código designación:" + designation.getId() + "\n" +
                "Para: " + designation.getMainReferee().getNombreCompleto() + "\n" +
                "En función de: ARBITRO\n \n" +
                "Fecha partido: " + new Date(designation.getMatch().getMatchDate().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Hora de comienzo: "+String.format("%.2f", designation.getMatch().getHour()) + "\n \n" +
                "Campo: " + designation.getMatch().getLocal().getStadium() + "\n \n" +
                "Competición: " + designation.getMatch().getCompetition().getName() +"\n \n"+
                "Equipo de casa: " + designation.getMatch().getLocal().getName() + "\n" +
                "Equipo visitante: " + designation.getMatch().getVisitor().getName() + "\n \n" +
                "ASISTENTE 1: " + designation.getAssistantReferee1().getNombreCompleto() + "\n" +
                "Telefono: " + designation.getAssistantReferee1().getTelfNumber() + "\n \n" +
                "ASISTENTE 2: " + designation.getAssistantReferee2().getNombreCompleto() +"\n"+
                "Telefono: " + designation.getAssistantReferee2().getTelfNumber();
        message.setText(contenido);
        emailSender.send(message);
    }

    @Override
    public void emailDesignacionAsistente1(Designation designation, String estado) throws UnsupportedEncodingException {
        if (estado.equals("CONFIRMACION")) {
            estado = "Le ha sido asignada la siguiente designación:";
        } else {
            estado = "Su designación ha sido cancelada:";
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Comité Árbitros")));
        message.setTo(designation.getAssistantReferee1().getEmail());
        message.setSubject("Aviso desde el Comité de Árbitros.");
        String contenido = estado+"\n \n" +
                "Código designación:" + designation.getId() + "\n" +
                "Para: " + designation.getAssistantReferee1().getNombreCompleto() + "\n" +
                "En función de: ASISTENTE 1\n \n" +
                "Fecha partido: " + new Date(designation.getMatch().getMatchDate().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Hora de comienzo: "+String.format("%.2f", designation.getMatch().getHour()) + "\n \n" +
                "Campo: " + designation.getMatch().getLocal().getStadium() + "\n \n" +
                "Competición: " + designation.getMatch().getCompetition().getName() +"\n \n"+
                "Equipo de casa: " + designation.getMatch().getLocal().getName() + "\n" +
                "Equipo visitante: " + designation.getMatch().getVisitor().getName() + "\n \n" +
                "ARBITRO: " + designation.getMainReferee().getNombreCompleto() + "\n" +
                "Telefono: " + designation.getMainReferee().getTelfNumber() + "\n \n" +
                "ASISTENTE 2: " + designation.getAssistantReferee2().getNombreCompleto() +"\n"+
                "Telefono: " + designation.getAssistantReferee2().getTelfNumber();
        message.setText(contenido);
        emailSender.send(message);
    }

    @Override
    public void emailDesignacionAsistente2(Designation designation, String estado) throws UnsupportedEncodingException {
        if (estado.equals("CONFIRMACION")) {
            estado = "Le ha sido asignada la siguiente designación:";
        } else {
            estado = "Su designación ha sido cancelada:";
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Aviso desde el Comité de Árbitros")));
        message.setTo(designation.getAssistantReferee1().getEmail());
        message.setSubject("Aviso desde el Comité de Árbitros.");
        String contenido = estado+"\n \n" +
                "Código designación:" + designation.getId() + "\n" +
                "Para: " + designation.getAssistantReferee2().getNombreCompleto() + "\n" +
                "En función de: ASISTENTE 2\n \n" +
                "Fecha partido: " + new Date(designation.getMatch().getMatchDate().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Hora de comienzo: "+String.format("%.2f", designation.getMatch().getHour()) + "\n \n" +
                "Campo: " + designation.getMatch().getLocal().getStadium() + "\n \n" +
                "Competición: " + designation.getMatch().getCompetition().getName() +"\n \n"+
                "Equipo de casa: " + designation.getMatch().getLocal().getName() + "\n" +
                "Equipo visitante: " + designation.getMatch().getVisitor().getName() + "\n \n" +
                "ARBITRO: " + designation.getMainReferee().getNombreCompleto() + "\n" +
                "Telefono: " + designation.getMainReferee().getTelfNumber() + "\n \n" +
                "ASISTENTE 1: " + designation.getAssistantReferee1().getNombreCompleto() +"\n"+
                "Telefono: " + designation.getAssistantReferee1().getTelfNumber();
        message.setText(contenido);
        emailSender.send(message);
    }
}
