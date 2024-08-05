package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.repository.VehicleRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @Transactional
@RequiredArgsConstructor
public class VehicleService {

  private final ValidationService validationService;
  private final CustomerRepository customerRepository;
  private final VehicleRepository vehicleRepository;

  private static List<SimpleRepairResponse> simpleVehicleResponses(Vehicle vehicle) {
    if (vehicle.getRepairs().isEmpty()) {
      return Collections.emptyList();
    }

    return vehicle.getRepairs().stream()
        .map(repair -> SimpleRepairResponse.builder()
            .id(repair.getId())
            .description(repair.getDescription())
            .entryDate(repair.getEntryDate())
            .exitDate(repair.getExitDate())
            .build()
        ).toList();
  }

  private VehicleResponse toVehicleResponse(Vehicle vehicle) {
    return VehicleResponse.builder()
        .id(vehicle.getId())
        .licensePlate(vehicle.getLicensePlate())
        .brand(vehicle.getBrand())
        .model(vehicle.getModel())
        .year(vehicle.getYear())
        .color(vehicle.getColor())
        .repairs(simpleVehicleResponses(vehicle))
        .build();
  }

  public VehicleResponse createVehicle(String customerId, CreateVehicleRequest request) {

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    validationService.validateRequest(request);

    var vehicle = new Vehicle();
    vehicle.setLicensePlate(request.getLicensePlate());
    vehicle.setBrand(request.getBrand());
    vehicle.setModel(request.getModel());
    vehicle.setYear(request.getYear());
    vehicle.setColor(request.getColor());
    vehicle.setCustomer(customer);

    vehicleRepository.save(vehicle);

    return toVehicleResponse(vehicle);
  }
}
