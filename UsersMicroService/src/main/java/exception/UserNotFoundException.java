package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User for session not found");
    }

    public UserNotFoundException(String login, String password){
        super("User with login: "+login+" password: "+password+" not found");
    }
}
