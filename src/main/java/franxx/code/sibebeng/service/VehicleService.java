package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.request.UpdateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.SimpleVehicleResponse;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.VehicleRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service @Transactional
@RequiredArgsConstructor
public class VehicleService {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  private final ValidationService validationService;
  private final EntityFinderService entityFinderService;
  private final VehicleRepository vehicleRepository;

  private List<SimpleRepairResponse> toSimpleRepairResponse(List<Repair> repairs) {
    return repairs.stream()
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
        .repairs(
            this.toSimpleRepairResponse(vehicle.getRepairs())
        ).build();
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

  public SimpleVehicleResponse createVehicle(CreateVehicleRequest request) {

    validationService.validateRequest(request);

    var customer = entityFinderService.findCustomer(request.getCustomerId());

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

    var vehicle = entityFinderService.findVehicle(
        request.getCustomerId(), request.getVehicleId()
    );

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

    var vehicle = entityFinderService.findVehicle(customerId, vehicleId);

    return toVehicleResponse(vehicle);
  }

  public void deleteVehicle(String customerId, String vehicleId) {

    var vehicle = entityFinderService.findVehicle(customerId, vehicleId);

    if (!vehicle.getRepairs().isEmpty()) {
      throw new ResponseStatusException(
          CONFLICT,
          "cannot delete vehicle, as it is still linked to existing repairs."
      );
    }

    vehicleRepository.delete(vehicle);
  }
}
