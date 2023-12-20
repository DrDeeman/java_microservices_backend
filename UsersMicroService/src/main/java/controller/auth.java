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

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class auth {

    @Autowired
    DAOUsers dUsers;

    @Autowired
    CustomUserDetail cud;


    private final AuthenticationManager authManager;

    public auth(AuthenticationManager authenticationManager) {
        this.authManager = authenticationManager;
    }

    @GetMapping(value="/auth")
    public eUsers getAuth(HttpSession session)throws UserNotFoundException{
        //Integer id_user = (Integer)session.getAttribute("user");
        SecurityContextHolder.setContext((SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(this.authManager.authenticate(auth).isAuthenticated());
        System.out.println("test name "+auth.getName());
        System.out.println(auth.getPrincipal());

        return null;
    }

    @GetMapping(value="/logout")
    public void logout(){

    }

    public record LoginRequest(String login, String password){}
    @PostMapping(value="/login")
    public eUsers login(@RequestBody LoginRequest lr, HttpSession session)
    throws UserNotFoundException{
        Authentication authReq = new UsernamePasswordAuthenticationToken(lr.login(),lr.password());
        Authentication auth = this.authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT",sc);
       return (eUsers)auth.getPrincipal();
        }

    }

