package DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import service.JwtService;

@Component
public class DAOJwt {

    @Autowired
    JwtService jwt;

    @Autowired
    ObjectMapper mapper;

    public ObjectNode getTokens(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        UserDetails user = (UserDetails)auth.getPrincipal();
        String accessToken = this.jwt.generateToken(user,true);
        String refreshToken = this.jwt.generateToken(user,false);
        ObjectNode body = this.mapper.createObjectNode();
        body.put("accessToken",accessToken);
        body.put("refreshToken",refreshToken);
        return body;
    }



}
