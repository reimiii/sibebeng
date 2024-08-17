package franxx.code.sibebeng.dto.vehicle.response;

import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
  private String id;
  private String licensePlate;
  private String model;
  private String brand;
  private String year;
  private String color;

  @Builder.Default
  private List<SimpleRepairResponse> repairs = new ArrayList<>();
}
