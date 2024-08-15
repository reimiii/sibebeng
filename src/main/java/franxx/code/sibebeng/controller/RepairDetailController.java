package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.repairdetail.request.CreateRepairDetailRequest;
import franxx.code.sibebeng.dto.repairdetail.request.UpdateRepairDetailRequest;
import franxx.code.sibebeng.dto.repairdetail.response.RepairDetailResponse;
import franxx.code.sibebeng.service.RepairDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController @RequiredArgsConstructor
@RequestMapping(
    path = "/api/customers/{customerId}/vehicles/{vehicleId}/repairs/{repairId}/details"
)
public class RepairDetailController {

  private final RepairDetailService repairDetailService;

  @PostMapping(
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> create(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @PathVariable(name = "repairId") String repairId,
      @RequestBody CreateRepairDetailRequest request
  ) {

    request.setCustomerId(customerId);
    request.setVehicleId(vehicleId);
    request.setRepairId(repairId);

    var rd = repairDetailService.createRepairDetail(request);
    var response = WebResponse.<RepairDetailResponse, Void>builder()
        .message("repair detail added successfully")
        .data(rd)
        .build();

    return ResponseEntity
        .status(CREATED)
        .body(response);
  }

  @PatchMapping(
      path = "/{repairDetailId}",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> update(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @PathVariable(name = "repairId") String repairId,
      @PathVariable(name = "repairDetailId") String repairDetailId,
      @RequestBody UpdateRepairDetailRequest request
  ) {

    request.setCustomerId(customerId);
    request.setVehicleId(vehicleId);
    request.setRepairId(repairId);
    request.setRepairDetailId(repairDetailId);

    var rd = repairDetailService.updateRepairDetail(request);

    var response = WebResponse.<RepairDetailResponse, Void>builder()
        .message("repair detail updated successfully")
        .data(rd)
        .build();

    return ResponseEntity.ok(response);
  }

  @DeleteMapping(
      path = "/{repairDetailId}",
      produces = APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> delete(
      @PathVariable(name = "customerId") String customerId,
      @PathVariable(name = "vehicleId") String vehicleId,
      @PathVariable(name = "repairId") String repairId,
      @PathVariable(name = "repairDetailId") String repairDetailId
  ) {

    repairDetailService.deleteRepairDetail(
        customerId, vehicleId, repairId, repairDetailId
    );

    var response = WebResponse.<String, Void>builder()
        .message("repair detail deleted successfully")
        .data("OK")
        .build();

    return ResponseEntity.ok(response);
  }
}
