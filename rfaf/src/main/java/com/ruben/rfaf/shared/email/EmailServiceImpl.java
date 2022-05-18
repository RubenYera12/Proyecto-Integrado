package com.ruben.rfaf.shared.email;

import com.ruben.rfaf.designation.domain.Designation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender emailSender;

    public void emaiConfirmacion(Designation designation) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Aviso desde el Comité de Árbitros")));
        message.setTo(designation.getMainReferee().getEmail());
        message.setSubject("Su reserva ha sido confirmada.");
        String contenido = "Buenas " + designation.getMainReferee().getName() + ", le informamos que su viaje con destino " + reserva.getCiudadDestino() + " ha sido confirmado.\n" +
                "Datos de la reserva:\n" + "Fecha del viaje: " + new Date(reserva.getFechaReserva().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + String.format("%.2f", reserva.getHoraReserva()) + "\n Un saludo.";
        message.setText(contenido);
        emailSender.send(message);
    }
}
