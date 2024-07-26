package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleRepositoryTest {

  @Autowired
  private VehicleRepository vehicleRepository;

  @Autowired
  private CustomerRepository customerRepository;

  private Customer customer;

  @BeforeEach
  void setUp() {
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();

    customer = new Customer();
    customer.setName("Mee");
    customer.setAddress("Bogor");
    customer.setEmail("imiia71");
    customer.setPhoneNumber("09090");

    customerRepository.save(customer);
  }

  @Test
  void insertVehicle() {

    // check uuid ada apa engga
    assertNotNull(customer.getId());
    System.out.println(customer.getId());

    var vehicle = new Vehicle();
    vehicle.setLicensePlate("000011");
    vehicle.setBrand("Toyota");
    vehicle.setModel("Avanza");
    vehicle.setYear("2002");
    vehicle.setColor("red");
    vehicle.setCustomer(customer);

    vehicleRepository.save(vehicle);
    assertNotNull(vehicle.getId());
    assertNotNull(vehicle.getCustomer());

    assertEquals("Mee", vehicle.getCustomer().getName());
    assertEquals(vehicle.getCustomer().getId(), customer.getId());
  }

  @Test
  void updateVehicle() {

    // check uuid ada apa engga
    assertNotNull(customer.getId());
    System.out.println(customer.getId());

    var vehicle = new Vehicle();
    vehicle.setLicensePlate("000011");
    vehicle.setBrand("Toyota");
    vehicle.setModel("Avanza");
    vehicle.setYear("2002");
    vehicle.setColor("red");
    vehicle.setCustomer(customer);

    vehicleRepository.save(vehicle);
    assertNotNull(vehicle.getId());
    assertNotNull(vehicle.getCustomer());

    assertEquals("Mee", vehicle.getCustomer().getName());
    assertEquals(vehicle.getCustomer().getId(), customer.getId());

    // update

    Vehicle updated = vehicleRepository.findById(vehicle.getId()).orElseThrow();
    updated.setModel("Xenia");
    vehicleRepository.save(updated);

    assertNotEquals(vehicle.getModel(), updated.getModel());
  }
  @Test
  void deletedVehicle() {

    // check uuid ada apa engga
    assertNotNull(customer.getId());
    System.out.println(customer.getId());

    var vehicle = new Vehicle();
    vehicle.setLicensePlate("000011");
    vehicle.setBrand("Toyota");
    vehicle.setModel("Avanza");
    vehicle.setYear("2002");
    vehicle.setColor("red");
    vehicle.setCustomer(customer);

    vehicleRepository.save(vehicle);
    assertNotNull(vehicle.getId());
    assertNotNull(vehicle.getCustomer());

    Vehicle updated = vehicleRepository.findById(vehicle.getId()).orElseThrow();
    assertEquals(1, vehicleRepository.count());

    vehicleRepository.delete(updated);

    assertEquals(0, vehicleRepository.count());
  }

}