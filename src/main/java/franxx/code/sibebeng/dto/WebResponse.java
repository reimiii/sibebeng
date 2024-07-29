package franxx.code.sibebeng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T1, T2> {
  private String message;
  private T1 data;
  private T2 errors;
}
