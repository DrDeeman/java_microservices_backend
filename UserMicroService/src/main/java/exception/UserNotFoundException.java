package exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User for session not found");
    }

    public UserNotFoundException(String login){
        super("User for login:"+login+" not found");
    }

    public UserNotFoundException(String login, String password){
        super("User with login: "+login+" password: "+password+" not found");
    }
}
