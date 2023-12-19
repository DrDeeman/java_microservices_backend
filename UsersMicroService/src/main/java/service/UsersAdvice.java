package service;

import exception.*;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UsersAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<List<String>> handleException(UserNotFoundException ex){
        List<String> arr = new ArrayList<>(1);
        arr.add(ex.getMessage());
        return new ResponseEntity<>(arr,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityException.class)
    public ResponseEntity<List<String>> handleEntityException(EntityException ex){
        return new ResponseEntity<>(ex.getMessages(),HttpStatus.BAD_REQUEST);
    }

}
