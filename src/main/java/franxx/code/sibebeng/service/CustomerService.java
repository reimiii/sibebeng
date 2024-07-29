package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
import franxx.code.sibebeng.dto.customer.request.UpdateCustomerRequest;
import franxx.code.sibebeng.dto.customer.response.CustomerResponse;
import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @Transactional
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final ValidationService validationService;

  private static CustomerResponse toCustomerResponse(Customer customer) {
    return CustomerResponse.builder()
        .id(customer.getId())
        .name(customer.getName())
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .address(customer.getAddress())
        .build();
  }

  public CustomerResponse createCustomer(CreateCustomerRequest request) {

    validationService.validateRequest(request);

    var customer = new Customer();
    customer.setName(request.getName());
    customer.setEmail(request.getEmail());
    customer.setPhoneNumber(request.getPhoneNumber());
    customer.setAddress(request.getAddress());

    customerRepository.save(customer);

    return toCustomerResponse(customer);
  }

  @Transactional(readOnly = true)
  public CustomerResponse getCustomer(String id) {
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    return toCustomerResponse(customer);
  }

  public CustomerResponse updateCustomer(UpdateCustomerRequest request) {

    validationService.validateRequest(request);

    var customer = customerRepository.findById(request.getId())
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    customer.setName(request.getName());
    customer.setEmail(request.getEmail());
    customer.setPhoneNumber(request.getPhoneNumber());
    customer.setAddress(request.getAddress());

    customerRepository.save(customer);

    return toCustomerResponse(customer);
  }
}
