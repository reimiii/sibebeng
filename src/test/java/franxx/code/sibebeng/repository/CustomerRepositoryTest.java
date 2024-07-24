package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

  // hhmmmmm what if customer is deleted and have one to many??
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private VehicleRepository vehicleRepository;

  private Customer customerR;
  private Vehicle vehicle;

  @BeforeEach
  void setUp() {
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
    var customer = new Customer();
    customer.setName("Hilmi");
    customer.setPhoneNumber("00911");
    customer.setAddress("Bogor");
    customer.setEmail("imiia75775@gmail.com");

    assertNull(customer.getId());

    // simpan ke db
    customer = customerRepository.save(customer);

    assertNotNull(customer.getId());
    assertNotNull(customer.getName());
    assertNotNull(customer.getAddress());
    assertNotNull(customer.getPhoneNumber());
    assertNotNull(customer.getEmail());

    Customer id = customerRepository.findById(customer.getId()).orElse(null);

    assertNotNull(id);
    assertEquals(customer.getName(), id.getName());
    assertEquals(2, customerRepository.count());
  }

  @Test
  void update() {

    var customer = new Customer();
    customer.setName("Hilmi");
    customer.setPhoneNumber("00911");
    customer.setAddress("Bogor");
    customer.setEmail("imiia75775@gmail.com");

    assertNull(customer.getId());

    customerRepository.save(customer);

    Customer customer1 = customerRepository.findById(customer.getId()).orElseThrow();
    customer1.setName("Hilmi AM");
    customerRepository.save(customer1);

    assertNotEquals(customer1.getName(), customer.getName());

  }

  @Test
  void deleted() {

    var customer = new Customer();
    customer.setName("Hilmi");
    customer.setPhoneNumber("00911");
    customer.setAddress("Bogor");
    customer.setEmail("imiia75775@gmail.com");

    assertNull(customer.getId());

    customerRepository.save(customer);

    assertEquals(2, customerRepository.count());

    Customer customer1 = customerRepository.findById(customer.getId()).orElseThrow();
    customerRepository.delete(customer1);

    assertEquals(1, customerRepository.count());
  }

  @Test
  void deleteCustomerHaveVehicle() {
    assertEquals(1, customerRepository.count());
    assertEquals(1, vehicleRepository.count());
    customerRepository.delete(customerR);

    assertEquals(0, customerRepository.count());
    assertEquals(0, vehicleRepository.count());
  }

}