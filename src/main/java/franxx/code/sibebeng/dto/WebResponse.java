package franxx.code.sibebeng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
  private String message;
  private T data;
  private List<String> errors;
}
