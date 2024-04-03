package exception;

import entity.eUsers;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Set;

public class EntityFieldException extends RuntimeException{

    private List<ObjectError> errors;
    public EntityFieldException(List<ObjectError> err){
          this.errors = err;
    }

    public EntityFieldException( Set<ConstraintViolation<Object>> err){
        this.errors = err.stream().map(e->(ObjectError)new FieldError("eUsers","",e.getMessage())).toList();
    }

    public List<String> getMessages(){
        return this.errors.stream()
                .filter(err->err instanceof FieldError)
                .map(err->err.getDefaultMessage())
                .toList();
    }
}
