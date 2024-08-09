package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.request.UpdateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.SimpleVehicleResponse;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    request.setCustomerId(customerId);

    var vehicle = vehicleService.createVehicle(request);

    var response = WebResponse.<SimpleVehicleResponse, Void>builder()
        .message("vehicle added successfully")
        .data(vehicle)
        .build();

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @PutMapping(
      path = "/{vehicleId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> update(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @RequestBody UpdateVehicleRequest request
  ) {

    request.setCustomerId(customerId);
    request.setVehicleId(vehicleId);

    var vehicle = vehicleService.updateVehicle(request);

    var response = WebResponse.<SimpleVehicleResponse, Void>builder()
        .message("vehicle updated successfully")
        .data(vehicle)
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @GetMapping(
      path = "/{vehicleId}",
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> get(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId
  ) {

    var vehicle = vehicleService.getDetailVehicle(customerId, vehicleId);

    var response = WebResponse.<VehicleResponse, Void>builder()
        .message("vehicle retrieved successfully")
        .data(vehicle)
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}