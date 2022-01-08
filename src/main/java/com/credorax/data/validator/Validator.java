package com.credorax.data.validator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Validator {

    boolean isValid;
    Map<String, String> errors;

    public Validator() {
        isValid = true;
    }

    public void addError(String key, String error) {
        isValid= false;
        this.errors = Objects.requireNonNullElseGet(this.errors, HashMap::new);
        this.errors.put(key, error);
    }

    public Map<String, String> getErrors() {
        return Objects.requireNonNullElseGet(this.errors, Collections::emptyMap);
    }
}
