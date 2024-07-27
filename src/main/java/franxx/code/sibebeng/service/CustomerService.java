package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequestDto;
import franxx.code.sibebeng.dto.customer.response.CustomerResponseDto;
import franxx.code.sibebeng.entity.Customer;
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

  public CustomerResponseDto createCustomer(CreateCustomerRequestDto request) {

    validationService.validateRequest(request);

    var customer = new Customer();
    customer.setName(request.getName());
    customer.setEmail(request.getEmail());
    customer.setPhoneNumber(request.getPhoneNumber());
    customer.setAddress(request.getAddress());

    customerRepository.save(customer);

    return CustomerResponseDto.builder()
        .id(customer.getId())
        .name(customer.getName())
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .address(customer.getAddress())
        .build();
  }
}
