package franxx.code.sibebeng.dto.customer.response;

import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailResponse {
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private String address;
  private List<VehicleResponse> vehicles;
}
