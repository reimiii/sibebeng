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
public class UpdateRepairDetailRequest {
  @JsonIgnore @NotBlank
  private String customerId;

  @JsonIgnore @NotBlank
  private String vehicleId;

  @JsonIgnore @NotBlank
  private String repairId;

  @JsonIgnore @NotBlank
  private String repairDetailId;

  private String issueDescription;

  private String repairAction;

  @Pattern(regexp = "PAID|UNPAID|CANCELED", message = "Invalid status payment")
  private String status;

  @Min(0)
  private Long price;
}
