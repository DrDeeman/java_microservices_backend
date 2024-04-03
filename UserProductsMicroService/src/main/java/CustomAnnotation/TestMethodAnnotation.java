package CustomAnnotation;

import CustomAnnotation.Validators.TestMethodAnnotationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR })
@Constraint(validatedBy = TestMethodAnnotationValidator.class)
public @interface TestMethodAnnotation {
    String message() default "{validator.not_valid_param}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
