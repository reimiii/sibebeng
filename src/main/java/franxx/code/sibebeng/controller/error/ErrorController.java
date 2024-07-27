package franxx.code.sibebeng.controller.error;

import franxx.code.sibebeng.dto.WebResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<WebResponse<Void>> constraintViolation(ConstraintViolationException violationException) {

    List<String> errors = violationException.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toList());

    WebResponse<Void> response = WebResponse.<Void>builder()
        .message("validation errors")
        .errors(errors)
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(response);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception) {
    List<String> errors = new ArrayList<>();
    if (exception.getReason() != null) {
      errors.add(exception.getReason());
    }
    return ResponseEntity.status(exception.getStatusCode())
        .body(WebResponse.<String>builder().errors(errors).build());
  }
}
