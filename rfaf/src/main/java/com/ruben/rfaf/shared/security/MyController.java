package com.ruben.rfaf.shared.security;

import com.ruben.rfaf.acta.domain.Acta;
import com.ruben.rfaf.acta.infraestructure.repository.ActaRepository;
import com.ruben.rfaf.referee.application.RefereeService;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.repository.RefereeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class MyController {

    private RefereeRepository refereeRepository;
    private ActaRepository actaRepository;
    private final LoginController loginController;
    private HttpServletRequest request;
    private HttpServletResponse response;
    public Boolean isAdmin(){
        HttpSession sesion = request.getSession();
        String token = (String) sesion.getAttribute("token");

        return verifyToken(token);
    }

    @GetMapping("api/logout")
    public void logout(){
        HttpSession sesion = request.getSession();
        sesion.invalidate();
    }

    public Referee logged(){
        HttpSession sesion = request.getSession();
        String token = (String) sesion.getAttribute("token");

        final String secretKey = "mySecretKey";
        final String prefix = "Bearer ";
        try {
            String jwToken = token.replace(prefix, "");
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwToken).getBody();
            Optional<Referee> referee = refereeRepository.findByEmailIgnoreCase(claims.getSubject());
            if (referee.isEmpty()){
                return null;
            }else {
                return referee.get();
            }
        } catch (Exception e) {
            return null;
        }
    }


    private boolean verifyToken(String token) {

        final String secretKey = "mySecretKey";
        final String prefix = "Bearer ";
        try {
            String jwToken = token.replace(prefix, "");
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwToken).getBody();
            Optional<Referee> referee = refereeRepository.findByEmailIgnoreCase(claims.getSubject());
            if (referee.isEmpty()){
                System.out.println("NO HAS INICIADO SESIÓN");
                return false;
            }else {
                System.out.println("CHECKING LOGIN");
                return referee.get().getAdmin();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefereeActa(String acta_id) {
        HttpSession sesion = request.getSession();
        String token = (String) sesion.getAttribute("token");

        final String secretKey = "mySecretKey";
        final String prefix = "Bearer ";
        try {
            String jwToken = token.replace(prefix, "");
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwToken).getBody();
            Optional<Referee> referee = refereeRepository.findByEmailIgnoreCase(claims.getSubject());
            Optional<Acta> acta = actaRepository.findById(acta_id);
            if (referee.isEmpty()||acta.isEmpty()){
                return false;
            }else {
                return referee.get().getId().equals(acta.get().getDesignation().getMainReferee().getId());
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isActaCerrada(String acta_id){
        Optional<Acta> acta = actaRepository.findById(acta_id);
        if (acta.isEmpty()){
            return true;
        }
        if (acta.get().getFinalizado()==null)
            return false;
        return acta.get().getFinalizado();
    }
}
