package franxx.code.sibebeng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableData<T> {

  private List<T> content;
  private Integer currentPage;
  private Integer currentSize;
  private Boolean hasNext;
  private Boolean hasPrevious;
  private Integer numberOfElements;
  private Integer totalPages;
  private Long totalElements;

}
