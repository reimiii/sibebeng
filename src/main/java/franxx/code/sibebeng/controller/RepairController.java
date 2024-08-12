package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.repair.request.CreateRepairRequest;
import franxx.code.sibebeng.dto.repair.request.UpdateRepairRequest;
import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
import franxx.code.sibebeng.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController @RequiredArgsConstructor
@RequestMapping(path = "/api/customers/{customerId}/vehicles/{vehicleId}/repairs")
public class RepairController {

  private final RepairService repairService;

  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> create(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @RequestBody CreateRepairRequest request
  ) {

    request.setCustomerId(customerId);
    request.setVehicleId(vehicleId);

    var repair = repairService.createRepair(request);
    var response = WebResponse.<SimpleRepairResponse, Void>builder()
        .message("repair added successfully")
        .data(repair)
        .build();

    return ResponseEntity
        .status(CREATED)
        .body(response);
  }

  @PatchMapping(
      path = "/{repairId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> update(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @PathVariable(name = "repairId") String repairId,
      @RequestBody UpdateRepairRequest request
  ) {

    request.setCustomerId(customerId);
    request.setVehicleId(vehicleId);
    request.setRepairId(repairId);

    var repair = repairService.updateRepair(request);
    var response = WebResponse.<SimpleRepairResponse, Void>builder()
        .message("repair updated successfully")
        .data(repair)
        .build();

    return ResponseEntity
        .ok(response);
  }

  @DeleteMapping(path = "/{repairId}")
  public ResponseEntity<?> delete(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @PathVariable(name = "repairId") String repairId
  ) {

    repairService.deleteRepair(customerId, vehicleId, repairId);

    var response = WebResponse.<String, Void>builder()
        .message("repair deleted successfully")
        .data("OK")
        .build();

    return ResponseEntity.ok(response);
  }
}
