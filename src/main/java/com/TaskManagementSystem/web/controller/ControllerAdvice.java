package com.TaskManagementSystem.web.controller;

import com.TaskManagementSystem.domain.exception.ExceptionBody;
import com.TaskManagementSystem.domain.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Validation failed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class), examples = @ExampleObject(value = "{\"message\": \"Validation failed.\",\"errors\": {\"id\": \"Id must not be null.\"}}"))),
    @ApiResponse(responseCode = "401", description = "Authentication failed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class), examples = @ExampleObject(value = "{\"message\": \"Authentication failed.\"}"))),
    @ApiResponse(responseCode = "403", description = "Access denied.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class), examples = @ExampleObject(value = "{\"message\": \"Access denied.\"}"))),
    @ApiResponse(responseCode = "404", description = "Resource not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class), examples = @ExampleObject(value = "{\"message\": \"Resource not found.\"}"))),
    @ApiResponse(responseCode = "500", description = "Internal error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class), examples = @ExampleObject(value = "{\"message\": \"Internal error.\"}")))
}
)
public class ControllerAdvice {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionBody handleResourceNotFound(
      final ResourceNotFoundException e
  ) {
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleIllegalState(final IllegalStateException e) {
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler({AccessDeniedException.class,
      org.springframework.security.access.AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ExceptionBody handleAccessDenied() {
    return new ExceptionBody("Access denied.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleMethodArgumentNotValid(
      final MethodArgumentNotValidException e
  ) {
    ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
    List<FieldError> errors = e.getBindingResult().getFieldErrors();
    exceptionBody.setErrors(errors.stream()
        .collect(Collectors.toMap(FieldError::getField,
            FieldError::getDefaultMessage)));
    return exceptionBody;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleConstraintViolation(
      final ConstraintViolationException e
  ) {
    ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
    exceptionBody.setErrors(e.getConstraintViolations().stream()
        .collect(Collectors.toMap(
            violation -> violation.getPropertyPath().toString(),
            ConstraintViolation::getMessage
        )));
    return exceptionBody;
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleAuthentication(final AuthenticationException e) {
    return new ExceptionBody("Authentication failed.");
  }


  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionBody handleException(final Exception e) {
    return new ExceptionBody("Internal error.");
  }

}
