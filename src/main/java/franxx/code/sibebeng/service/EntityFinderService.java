package franxx.code.sibebeng.service;

import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.RepairDetail;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.repository.RepairDetailRepository;
import franxx.code.sibebeng.repository.RepairRepository;
import franxx.code.sibebeng.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Service @RequiredArgsConstructor
public class EntityFinderService {

  private final CustomerRepository customerRepository;
  private final VehicleRepository vehicleRepository;
  private final RepairRepository repairRepository;
  private final RepairDetailRepository repairDetailRepository;

  public Customer findCustomer(String customerId) {
    return customerRepository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "customer not found"));
  }

  public Vehicle findVehicle(String customerId, String vehicleId) {
    var customer = findCustomer(customerId);
    return vehicleRepository.findByCustomerAndId(customer, vehicleId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "vehicle not found"));
  }

  public Repair findRepair(String customerId, String vehicleId, String repairId) {
    var vehicle = findVehicle(customerId, vehicleId);
    return repairRepository.findByVehicleAndId(vehicle, repairId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "repair not found"));
  }

  public RepairDetail findRepairDetail(String customerId, String vehicleId, String repairId, String repairDetailId) {
    var repair = findRepair(customerId, vehicleId, repairId);
    return repairDetailRepository.findByRepairAndId(repair, repairDetailId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "repair detail not found"));
  }
}
