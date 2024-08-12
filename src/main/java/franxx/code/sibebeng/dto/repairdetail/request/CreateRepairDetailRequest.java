package franxx.code.sibebeng.dto.repairdetail.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class CreateRepairDetailRequest {
  @JsonIgnore @NotBlank
  private String customerId;

  @JsonIgnore @NotBlank
  private String vehicleId;

  @JsonIgnore @NotBlank
  private String repairId;

  @NotBlank
  private String issueDescription;

  @NotBlank
  private String repairAction;

  @NotBlank @Pattern(regexp = "PAID|UNPAID|CANCELED", message = "Invalid status payment")
  private String status = "UNPAID";

  @NotBlank @Min(0)
  private Long price;
}
