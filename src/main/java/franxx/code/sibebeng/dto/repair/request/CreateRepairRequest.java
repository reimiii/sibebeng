package franxx.code.sibebeng.dto.repair.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class CreateRepairRequest {

  @JsonIgnore @NotBlank
  private String customerId;

  @JsonIgnore @NotBlank
  private String vehicleId;

  @NotBlank
  private String entryDate;

  private String exitDate;

  @NotBlank
  private String description;
}
