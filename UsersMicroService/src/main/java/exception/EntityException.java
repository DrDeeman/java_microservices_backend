package exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityException extends RuntimeException{

    private List<ObjectError> list_errors;
    public EntityException(List<ObjectError> errors){
         super();
         this.list_errors = errors;
    }

    public List<String> getMessages(){

        List<String> errors = new ArrayList<>(1);
        for(Object obj : this.list_errors) {
            if(obj instanceof FieldError)
                errors.add(((FieldError)obj).getDefaultMessage());
        }

        return errors;
    }
}
