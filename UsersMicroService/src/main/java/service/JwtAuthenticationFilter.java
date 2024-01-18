package service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtService jwt;

    @Autowired
    private Logger logger;

    @Autowired
    private CustomUserDetail cud;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws IOException, ServletException {
      String hAuth = req.getHeader("Authorization");
    try {
        if (hAuth != null) {
            String[] pieceAuth = hAuth.split("\\s");
            if (pieceAuth[0].equals("Bearer")) {
               if(!this.jwt.isTokenExpired(pieceAuth[1])) throw new ExpiredJwtException(null,null,null);

                String login = this.jwt.getLogin(pieceAuth[1]);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = this.cud.loadUserByUsername(login);

                    if (this.jwt.isTokenValid(pieceAuth[1], user)) {
                        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(upat);
                        this.logger.info("User for login " + login + " was authorized");
                    }
                }
            }
        }

        fc.doFilter(req,res);

    }catch(ExpiredJwtException e) {
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setHeader("Content-type","application/text");
        res.getWriter().write("Jwt token expired");
    }

    }
}
