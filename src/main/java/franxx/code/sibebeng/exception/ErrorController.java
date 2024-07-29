package franxx.code.sibebeng.exception;

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
  public ResponseEntity<?> constraintViolation(ConstraintViolationException violationException) {

    List<String> errors = violationException.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toList());

    WebResponse<Void, List<String>> response = WebResponse.<Void, List<String>>builder()
        .message("validation errors")
        .errors(errors)
        .build();

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(response);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> apiException(ResponseStatusException exception) {

    var response = WebResponse.<Void, String>builder()
        .message(exception.getReason())
        .errors(exception.getStatusCode().toString())
        .build();

    return ResponseEntity
        .status(exception.getStatusCode())
        .body(response);
  }
}
