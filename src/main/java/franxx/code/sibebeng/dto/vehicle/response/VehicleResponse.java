package franxx.code.sibebeng.dto.vehicle.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
