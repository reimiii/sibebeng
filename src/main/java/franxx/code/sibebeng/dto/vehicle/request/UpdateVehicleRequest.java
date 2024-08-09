package franxx.code.sibebeng.dto.vehicle.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class UpdateVehicleRequest {

  @JsonIgnore @NotBlank
  private String customerId;

  @JsonIgnore @NotBlank
  private String vehicleId;

  @NotBlank
  private String licensePlate;

  @NotBlank
  private String brand;

  @NotBlank
  private String model;

  @NotBlank @Size(max = 4)
  private String year;

  @NotBlank
  private String color;
}
