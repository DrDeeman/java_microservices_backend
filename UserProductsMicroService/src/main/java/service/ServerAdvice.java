package service;

import exception.EntityFieldException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ServerAdvice {

     @ExceptionHandler({
             EntityFieldException.class
     })
    public ResponseEntity<List<String>> handlerCustomException(Exception e){

         List<String> errs = new ArrayList<>();
         HttpStatus code = HttpStatus.BAD_REQUEST;

         if(e instanceof EntityFieldException)
             errs = ((EntityFieldException) e).getMessages();


         return new ResponseEntity<>(errs,code);
     }


     @ExceptionHandler({
           ConstraintViolationException.class
     })
     public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e){
         return new ResponseEntity<>(e.getMessage().split(":")[1],HttpStatus.BAD_REQUEST);
     }


    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<String> handlerBaseException(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }


}
