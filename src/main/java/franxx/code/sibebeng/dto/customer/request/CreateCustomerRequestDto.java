package franxx.code.sibebeng.dto.customer.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequestDto {
  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Address is required")
  private String address;

  @NotBlank(message = "Phone number is required")
  @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
  private String phoneNumber;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;
}
