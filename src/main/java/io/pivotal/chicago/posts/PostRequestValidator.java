package io.pivotal.chicago.posts;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PostRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "title", "blank");
    }
}
