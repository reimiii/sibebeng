package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.PageableData;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
import franxx.code.sibebeng.dto.customer.request.UpdateCustomerRequest;
import franxx.code.sibebeng.dto.customer.response.CustomerResponse;
import franxx.code.sibebeng.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        .message("customer added successfully")
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

    var customer = customerService.getCustomerDetail(id);

    WebResponse<CustomerResponse, Void> response = WebResponse.<CustomerResponse, Void>builder()
        .message("customer retrieved successfully")
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

    var response = WebResponse.<String, Void>builder()
        .message("customer deleted successfully")
        .data("OK")
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @GetMapping(
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> searchCustomer(
      @RequestParam(name = "keyword", required = false) String keyword,
      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
  ) {
    Page<CustomerResponse> customerResponses = customerService.getsAndSearchCustomers(keyword, page, size);

    PageableData<CustomerResponse> pageableData = PageableData.<CustomerResponse>builder()
        .content(customerResponses.getContent())
        .currentPage(customerResponses.getNumber())
        .currentSize(customerResponses.getSize())
        .hasNext(customerResponses.hasNext())
        .hasPrevious(customerResponses.hasPrevious())
        .numberOfElements(customerResponses.getNumberOfElements())
        .totalPages(customerResponses.getTotalPages())
        .totalElements(customerResponses.getTotalElements())
        .build();

    WebResponse<PageableData<CustomerResponse>, Void> response = WebResponse.<PageableData<CustomerResponse>, Void>builder()
        .message("customers retrieved successfully")
        .data(pageableData)
        .build();


    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}
