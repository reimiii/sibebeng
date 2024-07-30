package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
import franxx.code.sibebeng.dto.customer.request.UpdateCustomerRequest;
import franxx.code.sibebeng.dto.customer.response.CustomerResponse;
import franxx.code.sibebeng.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController @RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> create(
      @RequestBody CreateCustomerRequest request
  ) {
    CustomerResponse customer = customerService.createCustomer(request);

    WebResponse<CustomerResponse, Void> response = WebResponse.<CustomerResponse, Void>builder()
        .message("customer created successfully")
        .data(customer)
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @GetMapping(
      path = "/{customerId}",
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> get(
      @PathVariable(name = "customerId") String id
  ) {

    var customer = customerService.getCustomer(id);

    WebResponse<CustomerResponse, Void> response = WebResponse.<CustomerResponse, Void>builder()
        .message("one customer found")
        .data(customer)
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @PutMapping(
      path = "/{customerId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> update(
      @RequestBody UpdateCustomerRequest request,
      @PathVariable(name = "customerId") String customerId
  ) {

    request.setId(customerId);
    var customerResponse = customerService.updateCustomer(request);
    var response = WebResponse.<CustomerResponse, Void>builder()
        .message("customer updated successfully")
        .data(customerResponse)
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @DeleteMapping(
      path = "/{customerId}",
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> delete(
      @PathVariable(name = "customerId") String customerId
  ) {
    customerService.deleteCustomer(customerId);

    var response = WebResponse.<Void, Void>builder()
        .message("customer deleted successfully")
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}
