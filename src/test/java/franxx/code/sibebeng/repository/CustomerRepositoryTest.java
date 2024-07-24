package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
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
    customer = repository.save(customer);

    assertNotNull(customer.getId());
    assertNotNull(customer.getName());
    assertNotNull(customer.getAddress());
    assertNotNull(customer.getPhoneNumber());
    assertNotNull(customer.getEmail());

    Customer id = repository.findById(customer.getId()).orElse(null);

    assertNotNull(id);
    assertEquals(customer.getName(), id.getName());
    assertEquals(1, repository.count());
  }

  @Test
  void update() {

    var customer = new Customer();
    customer.setName("Hilmi");
    customer.setPhoneNumber("00911");
    customer.setAddress("Bogor");
    customer.setEmail("imiia75775@gmail.com");

    assertNull(customer.getId());

    repository.save(customer);

    Customer customer1 = repository.findById(customer.getId()).orElseThrow();
    customer1.setName("Hilmi AM");
    repository.save(customer1);

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

    repository.save(customer);

    assertEquals(1, repository.count());

    Customer customer1 = repository.findById(customer.getId()).orElseThrow();
    repository.delete(customer1);

    assertEquals(0, repository.count());
  }
}