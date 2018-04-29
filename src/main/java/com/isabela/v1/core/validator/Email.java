package com.isabela.v1.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;
//validate if string is an email
@org.hibernate.validator.constraints.Email
@Pattern(regexp = ".+@.+\\..+")
@ReportAsSingleViolation
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface Email {
    String message() default "Invalid e-mail";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}