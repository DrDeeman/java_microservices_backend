package CustomAnnotation.Validators;

import CustomAnnotation.TestMethodAnnotation;
import entity.eUsers;
import exception.EntityFieldException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class TestMethodAnnotationValidator implements ConstraintValidator<TestMethodAnnotation, Object[]> {



    @Override
    public void initialize(TestMethodAnnotation data){}

    @Override
    public boolean isValid(Object[] values, ConstraintValidatorContext cxt){
       return !((eUsers)values[0]).getName().equals(((eUsers)values[1]).getName());
    }
}
