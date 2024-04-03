package service;


import entity.eUsers;
import exception.EntityFieldException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.SmartValidator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class AdviceValidatorMethods {


    private Set<ConstraintViolation<Object>> validating(JoinPoint jp){
        Validator validator = ApplicationContextProvider.getApplicationContext().getBean(Validator.class);
        return Arrays.stream(jp.getArgs())
                .map(arg->validator.validate(arg))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Pointcut("execution(* DAO.DAOUserProducts.forTestValidationMethodAnnotation(..))")
    private void testValidationAOP(){}


    @Before(value="testValidationAOP()")
    public void beforeTestValidationAOP(JoinPoint jp){
        Set<ConstraintViolation<Object>> errors = validating(jp);
        if(!errors.isEmpty())
            throw new EntityFieldException(errors);
    }
}
