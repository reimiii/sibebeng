package franxx.code.sibebeng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<D, E> {
  private String message;
  private D data;
  private E errors;
}
