package CustomAnnotation;

import CustomAnnotation.Validators.TestAnnotationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TestAnnotationValidator.class)
public @interface TestAnnotation {
    String message() default "{validator.not_valid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
