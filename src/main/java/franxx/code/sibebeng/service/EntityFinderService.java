package franxx.code.sibebeng.service;

import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.CustomerRepository;
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

  public Customer findCustomer(String customerId) {
    return customerRepository.findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer not found"));
  }

  public Vehicle findVehicle(String customerId, String vehicleId) {
    var customer = findCustomer(customerId);
    return vehicleRepository.findByCustomerAndId(customer, vehicleId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Vehicle not found"));
  }

  public Repair findRepair(String customerId, String vehicleId, String repairId) {
    var vehicle = findVehicle(customerId, vehicleId);
    return repairRepository.findByVehicleAndId(vehicle, repairId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Repair not found"));
  }
}
