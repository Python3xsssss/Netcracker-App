package com.netcracker.skillstable.utils;

import com.google.common.collect.Ordering;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationHelper {
    public static void generateValidationException(BindingResult bindingResult) {
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .sorted(Ordering.natural())
                .collect(Collectors.toList());
        throw new ValidationException("Validation failed: " + messages);
    }
}
