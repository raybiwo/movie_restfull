package com.xsis.movieapi.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValidationError {
    private String field;
    private String message;
}
