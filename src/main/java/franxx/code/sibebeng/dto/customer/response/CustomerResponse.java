package franxx.code.sibebeng.dto.customer.response;

import franxx.code.sibebeng.dto.vehicle.response.SimpleVehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private String address;

  @Builder.Default
  private List<SimpleVehicleResponse> vehicles = new ArrayList<>();
}
