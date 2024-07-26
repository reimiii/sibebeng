package franxx.code.sibebeng.service;

import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final ValidationService validationService;

  // todo add some response
}
