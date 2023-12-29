package controller;

import DAO.DAOUsers;
import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import service.CustomUserDetail;

import java.security.Principal;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class auth {


    private final AuthenticationManager authManager;

    public auth(AuthenticationManager authenticationManager) {
        this.authManager = authenticationManager;
    }

    @GetMapping(value="/auth")
    public eUsers getAuth()throws UserNotFoundException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (eUsers)auth.getPrincipal();

    }

    @GetMapping(value="/logout")
    public void logout(){

    }

    public record LoginRequest(String login, String password){}
    @PostMapping(value="/login")
    public eUsers login(@RequestBody LoginRequest lr, HttpSession session)
    throws UserNotFoundException{
        System.out.println("start auth");
        Authentication authReq = new UsernamePasswordAuthenticationToken(lr.login(),lr.password());
        Authentication auth = this.authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT",sc);
       return (eUsers)auth.getPrincipal();
        }

    }

