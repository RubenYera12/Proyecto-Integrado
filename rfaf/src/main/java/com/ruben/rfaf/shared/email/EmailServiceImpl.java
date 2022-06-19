package com.ruben.rfaf.shared.email;

import com.ruben.rfaf.designation.domain.Designation;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    public void emailDesignacionArbitro(Designation designation, String estado) throws MessagingException, UnsupportedEncodingException {
        if (estado.equals("CONFIRMACION")) {
            estado = "Le ha sido asignada la siguiente designación:";
        } else {
            estado = "Su designación ha sido cancelada:";
        }
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ryeramartin@gmail.com", "Comité Árbitros");
        helper.setTo(designation.getMainReferee().getEmail());
        helper.setSubject("Aviso desde el Comité de Árbitros.");
        String contenido = estado + "\n \n" +
                "Código designación:" + designation.getId() + "\n" +
                "Para: " + designation.getMainReferee().getNombreCompleto() + "\n" +
                "En función de: ARBITRO\n \n" +
                "Fecha partido: " + new Date(designation.getMatch().getFecha().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Hora de comienzo: "+String.format("%.2f", designation.getMatch().getHora()) + "\n \n" +
                "Campo: " + designation.getMatch().getLocal().getStadium() + "\n \n" +
                "Competición: " + designation.getMatch().getCompetition().getName() +"\n \n"+
                "Equipo de casa: " + designation.getMatch().getLocal().getName() + "\n" +
                "Equipo visitante: " + designation.getMatch().getVisitor().getName() + "\n \n" +
                "ASISTENTE 1: " + designation.getAssistantReferee1().getNombreCompleto() + "\n" +
                "Telefono: " + designation.getAssistantReferee1().getTelfNumber() + "\n \n" +
                "ASISTENTE 2: " + designation.getAssistantReferee2().getNombreCompleto() +"\n"+
                "Telefono: " + designation.getAssistantReferee2().getTelfNumber();
        helper.setText(contenido);
        emailSender.send(message);
    }

    @Override
    public void emailDesignacionAsistente1(Designation designation, String estado) throws UnsupportedEncodingException, MessagingException {
        if (estado.equals("CONFIRMACION")) {
            estado = "Le ha sido asignada la siguiente designación:";
        } else {
            estado = "Su designación ha sido cancelada:";
        }
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ryeramartin@gmail.com", "Comité Árbitros");
        helper.setTo(designation.getAssistantReferee1().getEmail());
        helper.setSubject("Aviso desde el Comité de Árbitros.");
        String contenido = estado+"\n \n" +
                "Código designación:" + designation.getId() + "\n" +
                "Para: " + designation.getAssistantReferee1().getNombreCompleto() + "\n" +
                "En función de: ASISTENTE 1\n \n" +
                "Fecha partido: " + new Date(designation.getMatch().getFecha().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Hora de comienzo: "+String.format("%.2f", designation.getMatch().getHora()) + "\n \n" +
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
    public void emailDesignacionAsistente2(Designation designation, String estado) throws UnsupportedEncodingException, MessagingException {
        if (estado.equals("CONFIRMACION")) {
            estado = "Le ha sido asignada la siguiente designación:";
        } else {
            estado = "Su designación ha sido cancelada:";
        }
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ryeramartin@gmail.com", "Comité Árbitros");
        helper.setTo(designation.getAssistantReferee2().getEmail());
        helper.setSubject("Aviso desde el Comité de Árbitros.");
        String contenido = estado+"\n \n" +
                "Código designación:" + designation.getId() + "\n" +
                "Para: " + designation.getAssistantReferee2().getNombreCompleto() + "\n" +
                "En función de: ASISTENTE 2\n \n" +
                "Fecha partido: " + new Date(designation.getMatch().getFecha().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "Hora de comienzo: "+String.format("%.2f", designation.getMatch().getHora()) + "\n \n" +
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
