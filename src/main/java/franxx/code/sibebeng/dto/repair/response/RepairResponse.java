package franxx.code.sibebeng.dto.repair.response;

import franxx.code.sibebeng.dto.repairdetail.response.RepairDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class RepairResponse {

  private String id;
  private String description;
  private String entryDate;
  private String exitDate;

  @Builder.Default
  private List<RepairDetailResponse> repairDetails = new ArrayList<>();

}
