package franxx.code.sibebeng.dto.repairdetail.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class RepairDetailResponse {
  private String id;
  private String issueDescription;
  private String repairAction;
  private String status;
  private Long price;
}
