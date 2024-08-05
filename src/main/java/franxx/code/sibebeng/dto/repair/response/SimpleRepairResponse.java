package franxx.code.sibebeng.dto.repair.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class SimpleRepairResponse {

  private String id;
  private String description;
  private LocalDateTime entryDate;
  private LocalDateTime exitDate;

}
