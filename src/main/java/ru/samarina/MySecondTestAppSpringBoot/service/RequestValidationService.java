package ru.samarina.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.samarina.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.samarina.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import java.util.Objects;

@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult)
            throws ValidationFailedException, UnsupportedCodeException {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult.getFieldError().toString());
        }
        if (Objects.equals(bindingResult.getFieldValue("uid"), "123")) {
            throw new UnsupportedCodeException("Этот uid запрещён.");
        }
    }
}