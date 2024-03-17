package com.xsis.movieapi.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse <T> {

    // The status of the API response, indicating success or failure.
    @JsonProperty("success")
    private boolean success;

    // A human-readable message providing additional information about the API response.
    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<ValidationError> validationErrors;

    // The HTTP status code associated with the API response.
    @JsonIgnore
    private Integer httpStatus;

    // The data payload included in the API response, holding the actual content.
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * Creates an APIResponse for a successful operation.
     *
     * @param data             The data to include in the response.
     * @param message  A map containing response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a successful operation.
     */
    public static <T> APIResponse<T> ok(T data, String message) {
        return APIResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .httpStatus(HttpStatus.OK.value())
                .build();
    }

    /**
     * Creates an APIResponse for a validation error operation.
     *
     * @param message  A map containing response messages.
     * @param validationErrors              The key corresponding to the desired response message.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> validationError(String message, List<ValidationError> validationErrors) {
        return APIResponse.<T>builder()
                .success(false)
                .message(message)
                .validationErrors(validationErrors)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    /**
     * Creates an APIResponse for a Bad Request operation.
     *
     * @param message  A map containing response messages.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> badRequest(String message) {
        return APIResponse.<T>builder()
                .success(false)
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    /**
     * Creates an APIResponse for a not found operation.
     *
     * @param message  A map containing response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> notFound(String message) {
        return APIResponse.<T>builder()
                .success(false)
                .message(message)
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .build();
    }

    /**
     * Creates an APIResponse for a internal server error.
     *
     * @param message  A map containing response messages.
     * @param <T>              The type of data to be included in the response.
     * @return An APIResponse indicating a failed operation.
     */
    public static <T> APIResponse<T> internalServerError(String message) {
        return APIResponse.<T>builder()
                .success(false)
                .message(message)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}