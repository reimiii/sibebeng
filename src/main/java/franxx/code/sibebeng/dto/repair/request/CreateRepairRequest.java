package franxx.code.sibebeng.dto.repair.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid datetime format")
  private String entryDate;

  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$", message = "Invalid datetime format")
  private String exitDate;

  @NotBlank
  private String description;
}