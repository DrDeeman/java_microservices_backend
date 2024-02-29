package exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class EntityFieldException extends RuntimeException{

    private List<ObjectError> errors;
    public EntityFieldException(List<ObjectError> err){
          this.errors = err;
    }

    public List<String> getMessages(){
        return this.errors.stream()
                .filter(err->err instanceof FieldError)
                .map(err->err.getDefaultMessage())
                .toList();
    }
}
