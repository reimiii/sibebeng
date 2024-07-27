package franxx.code.sibebeng.dto.customer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private String address;
}
