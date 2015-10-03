package io.pivotal.chicago.error;

import org.springframework.validation.Errors;

public class InvalidResourceException extends RuntimeException {
    private Errors errors;

    public InvalidResourceException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
