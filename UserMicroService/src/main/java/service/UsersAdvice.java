package service;

import exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class UsersAdvice {

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            UserNotFoundException.class,
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class
    })
    public ResponseEntity<List<String>> handleException(Exception ex){
        List<String> arr = new ArrayList<>(1);


        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex instanceof InternalAuthenticationServiceException) {
            status = HttpStatus.UNAUTHORIZED;
            arr.add(ex.getMessage());
        }


        if(ex instanceof BadCredentialsException) {
            status = HttpStatus.BAD_REQUEST;
            arr.add(ex.getMessage());
        }

        if(ex instanceof DataIntegrityViolationException) {
            Pattern pattern = Pattern.compile("\\[{1}?\\w+:[^:]*:\\s?([^\\[\\]]*)\\]{1}?",Pattern.DOTALL);
            //regexp for this string
            //"could not execute statement [ERROR: duplicate key value violates unique constraint \"uni_login\"\n  Подробности: Key (login)=(demo) already exists.] [insert into users (email,login,name,password,id) values (?,?,?,?,?)]; SQL [insert into users (email,login,name,password,id) values (?,?,?,?,?)]; constraint [uni_login]";
          //  Pattern pattern = Pattern.compile("^[^:]+:(.*)\\.$",Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(ex.getMessage());
            if(m.find())
            arr.add(m.group(1));
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(arr,status);
    }


    @ExceptionHandler(EntityException.class)
    public ResponseEntity<List<String>> handleEntityException(EntityException ex){
        return new ResponseEntity<>(ex.getMessages(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherException(Exception ex){
        return new ResponseEntity<>("Has been wrong:"+ ex.getMessage(),HttpStatus.BAD_REQUEST);
    }


}
