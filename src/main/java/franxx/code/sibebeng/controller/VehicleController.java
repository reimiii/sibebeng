package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController @RequiredArgsConstructor
@RequestMapping(path = "/api/customers/{customerId}/vehicles")
public class VehicleController {

  private final VehicleService vehicleService;

  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> create(
      @PathVariable(name = "customerId") String customerId,
      @RequestBody CreateVehicleRequest request
  ) {

    VehicleResponse vehicle = vehicleService.createVehicle(customerId, request);

    WebResponse<VehicleResponse, Void> response = WebResponse.<VehicleResponse, Void>builder()
        .message("vehicle added successfully")
        .data(vehicle)
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }
}