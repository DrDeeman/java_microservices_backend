package service;

import entity.eUsers;
import exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;


@Service
public class JwtService {

    @Value(value = "${jwt.secret}")
    private String secret;

    private final static int timeRefreshToken = 86400000;
    private final static int timeAccessToken = 300000;



    public String generateToken(UserDetails user,boolean access) throws UserNotFoundException{
        Map<String, Object> data = new HashMap<>();
        if (user instanceof eUsers) {
            data.put("id", ((eUsers) user).getId());
            data.put("email", ((eUsers) user).getEmail());
            return Jwts.builder().setClaims(data).setSubject(((eUsers)user).getLogin())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() +
                            (access?JwtService.timeAccessToken:JwtService.timeRefreshToken)
                    ))
                    .signWith(SignatureAlgorithm.HS256,this.secret).compact();
        }

        throw new UserNotFoundException();
    }

    public boolean isTokenExpired(String token){
        System.out.println(this.getClaimFromToken(token,Claims::getExpiration));
        System.out.println(new Date());
        return new Date().before(this.getClaimFromToken(token,Claims::getExpiration));
    }

    public boolean isTokenValid(String token, UserDetails user){
        String jwtLogin = this.getClaimFromToken(token,Claims::getSubject);
        System.out.println("jwt login "+jwtLogin);
        System.out.println(this.isTokenExpired(token));
        if(user instanceof eUsers) {
            return ((eUsers) user).getLogin().equals(jwtLogin) && this.isTokenExpired(token);
        }
        return false;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> cr){
        return cr.apply(getAllBodyJwt(token));
    }

    public String getLogin(String token){
        return this.getClaimFromToken(token,Claims::getSubject);
    }


    private Claims getAllBodyJwt(String token){
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }

}
