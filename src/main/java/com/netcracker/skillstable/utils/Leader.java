package com.netcracker.skillstable.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LeaderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Leader {
    String message() default "This user is not available as a leader";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
