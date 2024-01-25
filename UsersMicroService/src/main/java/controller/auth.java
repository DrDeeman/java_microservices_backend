package controller;

import DAO.DAOJwt;
import DAO.DAOUsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.eUsers;
import exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import records.DataProfile;
import service.KafkaProducer;
import records.LoginRequest;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
public class auth {

    @Autowired
    private DAOJwt dao;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    KafkaProducer kp;

    @Autowired
    DAOUsers dUsers;



    private final AuthenticationManager authManager;

    public auth(AuthenticationManager authenticationManager) {
        this.authManager = authenticationManager;
    }


    @GetMapping(value="/auth")
    public eUsers getAuth() throws UserNotFoundException, JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        eUsers user =  (eUsers)auth.getPrincipal();
        return user;

    }

    @PatchMapping(value="/auth")
    public eUsers editProfile(@RequestBody DataProfile data){
        System.out.println("Start edit");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        eUsers user = (eUsers)auth.getPrincipal();
        this.dUsers.editUser(user,data);
        return user;
    }

    @GetMapping(value="/logout")
    public void logout(){

    }

    @GetMapping(value="/refresh_token")
    public ResponseEntity<String> refreshToken() throws JsonProcessingException {
        return new ResponseEntity<>(this.mapper.writeValueAsString(this.dao.getTokens()),HttpStatus.OK);
    }


    @PostMapping(value="/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest lr, HttpSession session)
            throws UserNotFoundException, JsonProcessingException {

        System.out.println("start auth");
        Authentication authReq = new UsernamePasswordAuthenticationToken(lr.login(),lr.password());
        Authentication auth = this.authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        UserDetails user = (UserDetails) auth.getPrincipal();
        ObjectNode body = this.dao.getTokens();
        ObjectNode tree = this.mapper.valueToTree(user);
        body.set("dataUser",tree);
        return new ResponseEntity<>(this.mapper.writeValueAsString(body),HttpStatus.OK);

        /*

        session.setAttribute("SPRING_SECURITY_CONTEXT",sc);
       return (eUsers)auth.getPrincipal();
       */

        }

    }

