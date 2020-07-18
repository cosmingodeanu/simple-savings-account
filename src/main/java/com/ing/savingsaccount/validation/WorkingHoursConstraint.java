package com.ing.savingsaccount.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WorkingHoursDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkingHoursConstraint {

    String message() default "{account.save.outsideHours}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
