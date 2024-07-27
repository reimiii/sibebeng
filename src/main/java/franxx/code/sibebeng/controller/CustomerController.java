package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequestDto;
import franxx.code.sibebeng.dto.customer.response.CustomerResponseDto;
import franxx.code.sibebeng.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<WebResponse<CustomerResponseDto>> create(
      @RequestBody CreateCustomerRequestDto request
  ) {
    CustomerResponseDto customer = customerService.createCustomer(request);

    WebResponse<CustomerResponseDto> response = WebResponse.<CustomerResponseDto>builder()
        .message("customer created successfully")
        .data(customer)
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }
}
