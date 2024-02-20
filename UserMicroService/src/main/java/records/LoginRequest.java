package records;

public record LoginRequest(
        String login,
        String password
){
    public LoginRequest(String login){
        this(login,"");
    }
}
