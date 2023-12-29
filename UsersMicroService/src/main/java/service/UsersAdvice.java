package service;

import exception.*;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UsersAdvice {

    @ExceptionHandler({UserNotFoundException.class,BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<List<String>> handleException(RuntimeException ex){
        List<String> arr = new ArrayList<>(1);
        arr.add(ex.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex instanceof InternalAuthenticationServiceException)
            status = HttpStatus.UNAUTHORIZED;


        if(ex instanceof BadCredentialsException)
            status = HttpStatus.BAD_REQUEST;



        return new ResponseEntity<>(arr,status);
    }


    @ExceptionHandler(EntityException.class)
    public ResponseEntity<List<String>> handleEntityException(EntityException ex){
        return new ResponseEntity<>(ex.getMessages(),HttpStatus.BAD_REQUEST);
    }


}
