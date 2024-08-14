package franxx.code.sibebeng.controller;

import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.repairdetail.request.CreateRepairDetailRequest;
import franxx.code.sibebeng.dto.repairdetail.response.RepairDetailResponse;
import franxx.code.sibebeng.service.RepairDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

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
}
