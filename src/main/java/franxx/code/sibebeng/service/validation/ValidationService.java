package franxx.code.sibebeng.service.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ValidationService {

  private final Validator validator;

  public void validatedRequest(Object request) {
    Set<ConstraintViolation<Object>> violations = validator.validate(request);

    if (!violations.isEmpty()) {

      List<String> errors = violations.stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.toList());

      throw new ConstraintViolationException(errors.toString(), violations);
    }
  }
}
