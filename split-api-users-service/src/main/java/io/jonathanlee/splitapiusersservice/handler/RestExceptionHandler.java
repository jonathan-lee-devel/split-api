package io.jonathanlee.splitapiusersservice.handler;

import io.jonathanlee.splitapiusersservice.error.ValidationErrorDto;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String ERRORS_FIELD = "errors";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request
  ) {
    List<ValidationErrorDto> validationErrorDtos = ex
        .getBindingResult()
        .getFieldErrors()
        .parallelStream()
        .map(fieldError -> new ValidationErrorDto(
            fieldError.getField(),
            fieldError.getDefaultMessage()
        )).toList();

    return ResponseEntity.status(status).body(Map.of(ERRORS_FIELD, validationErrorDtos));
  }

}
