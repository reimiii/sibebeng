package franxx.code.sibebeng.service.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service @RequiredArgsConstructor
public class ValidationService {

  private final Validator validator;

  public void validateRequest(Object request) {
    Set<ConstraintViolation<Object>> violations = validator.validate(request);

    if (!violations.isEmpty()) {

      List<String> errors = violations.stream()
          .map(ConstraintViolation::getMessage)
          .toList();

      throw new ConstraintViolationException(errors.toString(), violations);
    }
  }
}
