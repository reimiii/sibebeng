package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.request.UpdateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.SimpleVehicleResponse;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.repository.VehicleRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @Transactional
@RequiredArgsConstructor
public class VehicleService {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
            .entryDate(repair.getEntryDate().format(formatter))
            .exitDate(repair.getExitDate() != null ? repair.getExitDate().format(formatter) : null)
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

  private SimpleVehicleResponse toSimpleVehicleResponse(Vehicle vehicle) {
    return SimpleVehicleResponse.builder()
        .id(vehicle.getId())
        .licensePlate(vehicle.getLicensePlate())
        .brand(vehicle.getBrand())
        .model(vehicle.getModel())
        .year(vehicle.getYear())
        .color(vehicle.getColor())
        .build();
  }

  private Vehicle getVehicle(String customerId, String vehicleId) {
    var customer = customerRepository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    return vehicleRepository.findByCustomerAndId(customer, vehicleId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "vehicle not found"));
  }

  public SimpleVehicleResponse createVehicle(CreateVehicleRequest request) {

    validationService.validateRequest(request);

    var customer = customerRepository.findById(request.getCustomerId())
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));

    var vehicle = new Vehicle();
    vehicle.setLicensePlate(request.getLicensePlate());
    vehicle.setBrand(request.getBrand());
    vehicle.setModel(request.getModel());
    vehicle.setYear(request.getYear());
    vehicle.setColor(request.getColor());
    vehicle.setCustomer(customer);

    vehicleRepository.save(vehicle);

    return toSimpleVehicleResponse(vehicle);
  }

  public SimpleVehicleResponse updateVehicle(UpdateVehicleRequest request) {

    validationService.validateRequest(request);

    var vehicle = getVehicle(request.getCustomerId(), request.getVehicleId());

    vehicle.setLicensePlate(request.getLicensePlate());
    vehicle.setBrand(request.getBrand());
    vehicle.setModel(request.getModel());
    vehicle.setYear(request.getYear());
    vehicle.setColor(request.getColor());

    vehicleRepository.save(vehicle);

    return toSimpleVehicleResponse(vehicle);
  }

  @Transactional(readOnly = true)
  public VehicleResponse getDetailVehicle(String customerId, String vehicleId) {

    var vehicle = getVehicle(customerId, vehicleId);

    return toVehicleResponse(vehicle);
  }

  public void deleteVehicle(String customerId, String vehicleId) {

    var vehicle = getVehicle(customerId, vehicleId);

    if (!vehicle.getRepairs().isEmpty()) {
      throw new ResponseStatusException(CONFLICT, "customer still has vehicles");
    }

    vehicleRepository.delete(vehicle);
  }
}
