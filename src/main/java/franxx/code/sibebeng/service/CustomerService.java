package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
import franxx.code.sibebeng.dto.customer.request.UpdateCustomerRequest;
import franxx.code.sibebeng.dto.customer.response.CustomerDetailResponse;
import franxx.code.sibebeng.dto.customer.response.CustomerResponse;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import franxx.code.sibebeng.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
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
  public CustomerDetailResponse getCustomerDetail(String id) {
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    List<VehicleResponse> vehicleResponses = customer.getVehicles().stream()
        .map(vehicle -> VehicleResponse.builder()
            .id(vehicle.getId())
            .brand(vehicle.getBrand())
            .model(vehicle.getModel())
            .licensePlate(vehicle.getLicensePlate())
            .year(vehicle.getYear())
            .color(vehicle.getColor())
            .build()
        ).toList();

    return CustomerDetailResponse.builder()
        .id(customer.getId())
        .name(customer.getName())
        .address(customer.getAddress())
        .phoneNumber(customer.getPhoneNumber())
        .email(customer.getEmail())
        .vehicles(vehicleResponses)
        .build();
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

  public void deleteCustomer(String id) {

    var customer = customerRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    if (!customer.getVehicles().isEmpty()) {
      throw new ResponseStatusException(CONFLICT, "customer has related vehicle");
    }

    customerRepository.delete(customer);
  }

  @Transactional(readOnly = true)
  public Page<CustomerResponse> searchCustomer(
      String keyword,
      Integer page,
      Integer size
  ) {
    PageRequest pageable = PageRequest.of(page, size);
    Page<Customer> customerPage = customerRepository.findAll(CustomerSpecification.containsTextInAttributes(keyword), pageable);
    List<CustomerResponse> customerResponseList = customerPage.getContent()
        .stream()
        .map(CustomerService::toCustomerResponse)
        .toList();

    return new PageImpl<>(
        customerResponseList, pageable, customerPage.getTotalElements()
    );
  }

}
