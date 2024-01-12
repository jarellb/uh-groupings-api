package edu.hawaii.its.api.exception;

import edu.hawaii.its.api.type.ApiSubError;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class UhMemberNotFoundException extends ResponseStatusException {

    private final List<ApiSubError> subErrors;
    public UhMemberNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
        this.subErrors = new ArrayList<>();
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public UhMemberNotFoundException addSubError(ApiSubError subError) {
        this.subErrors.add(subError);
        return this;
    }

}
