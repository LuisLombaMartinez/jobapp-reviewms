package com.travis.jobapp.reviewms.exception;

import java.util.Date;
import java.util.List;

public class ValidationErrorDetails extends ErrorDetails {

    private List<String> errors;

    public ValidationErrorDetails(Date timestamp, String message, String details) {
        super(timestamp, message, details);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [timestamp=" + getTimestamp() +
                ", message=" + getMessage() +
                ", details=" + getDetails() +
                ", errors=" + getErrors() + "]";
    }

}
