package franxx.code.sibebeng.service;

import franxx.code.sibebeng.dto.repair.request.CreateRepairRequest;
import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.repository.RepairRepository;
import franxx.code.sibebeng.repository.VehicleRepository;
import franxx.code.sibebeng.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service @Transactional
@RequiredArgsConstructor
public class RepairService {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  private final ValidationService validationService;
  private final EntityFinderService entityFinderService;
  private final CustomerRepository customerRepository;
  private final VehicleRepository vehicleRepository;
  private final RepairRepository repairRepository;

  private SimpleRepairResponse toSimpleRepairResponse(Repair repair) {
    return SimpleRepairResponse.builder()
        .id(repair.getId())
        .entryDate(repair.getEntryDate().format(formatter))
        .exitDate(
            repair.getExitDate() != null
                ? repair.getExitDate().format(formatter)
                : null
        )
        .description(repair.getDescription())
        .build();
  }

  public SimpleRepairResponse createRepair(CreateRepairRequest request) {

    validationService.validateRequest(request);

    var vehicle = entityFinderService.findVehicle(
        request.getCustomerId(), request.getVehicleId()
    );

    var repair = new Repair();
    repair.setVehicle(vehicle);

    repair.setEntryDate(LocalDateTime.parse(request.getEntryDate(), formatter));
    repair.setExitDate(
        request.getExitDate() != null
            ? LocalDateTime.parse(request.getExitDate(), formatter)
            : null
    );
    repair.setDescription(request.getDescription());

    repairRepository.save(repair);

    return toSimpleRepairResponse(repair);
  }
}
