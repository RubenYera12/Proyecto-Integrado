package com.ruben.rfaf.shared.security;

import com.ruben.rfaf.referee.application.RefereeService;
import com.ruben.rfaf.referee.domain.Referee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    RefereeService refereeService;

    @GetMapping("api/login")
    public ResponseEntity<String> login(
            @RequestHeader("email") String email, @RequestHeader("password") String pwd,HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Referee referee = refereeService.findByEmail(email);
        String password = referee.getPassword();
        String id = referee.getId();

        if (!pwd.equals(password)) throw new Exception("Password erroneo");
        String rol = (referee.getAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";

        String token = getJWTToken(id,email, rol);
        HttpSession sesion = request.getSession();
        sesion.setAttribute("token", token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    private String getJWTToken(String id,String username, String rol) {

        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(rol);

        String token =
                Jwts.builder()
                        .setId((id))
                        .setSubject(username)
                        .claim(
                                "authorities",
                                grantedAuthorities.stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 600000))
                        .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                        .compact();

        return "Bearer " + token;
    }
}
