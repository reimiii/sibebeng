package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RepairRepositoryTest {

  @Autowired
  private RepairRepository repairRepository;

  @Autowired
  private VehicleRepository vehicleRepository;

  @Autowired
  private CustomerRepository customerRepository;

  private Customer customerR;
  private Vehicle vehicle;

  @BeforeEach
  void setUp() {
    repairRepository.deleteAll();
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();


    customerR = new Customer();
    customerR.setName("Mee");
    customerR.setAddress("Bogor");
    customerR.setEmail("imiia71");
    customerR.setPhoneNumber("09090");
    customerRepository.save(customerR);

    vehicle = new Vehicle();
    vehicle.setLicensePlate("000011");
    vehicle.setBrand("Toyota");
    vehicle.setModel("Avanza");
    vehicle.setYear("2002");
    vehicle.setColor("red");
    vehicle.setCustomer(customerR);
    vehicleRepository.save(vehicle);

  }

  @Test
  void insert() {
    var repair = new Repair();
    repair.setDescription("katanya panas");
    repair.setVehicle(vehicle);
    repair.setEntryDate(LocalDateTime.now());

    assertNull(repair.getId());

    repairRepository.save(repair);

    assertNotNull(repair.getId());
    assertNotNull(repair.getEntryDate());
    assertNotNull(repair.getDescription());
  }
}