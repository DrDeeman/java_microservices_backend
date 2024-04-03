package CustomAnnotation.Validators;

import CustomAnnotation.TestAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TestAnnotationValidator implements ConstraintValidator<TestAnnotation, String> {

    @Override
    public void initialize(TestAnnotation data){}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt){
        if(value == null || value.equals("testtt"))
             return false;
        return true;
    }
}
