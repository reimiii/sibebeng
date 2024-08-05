package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private VehicleRepository vehicleRepository;

  private Customer customer;

  private Vehicle vehicle;

  @BeforeEach
  void setUp() {
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();

    objectMapper = objectMapper
        .configure(SerializationFeature.INDENT_OUTPUT, true);

    customer = new Customer();
    customer.setName("Hilmi AM");
    customer.setEmail("mail@mail.com");
    customer.setPhoneNumber("0123456789123");
    customer.setAddress("BGR");
    customerRepository.save(customer);

    vehicle = new Vehicle();
    vehicle.setCustomer(customer);
    vehicle.setBrand("Toyota");
    vehicle.setModel("Kijang");
    vehicle.setLicensePlate("F 22 OO");
    vehicle.setYear("2016");
    vehicle.setColor("Black Blue");
    vehicleRepository.save(vehicle);
  }

  @AfterEach
  void tearDown() {
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();
  }

  @Test
  void createBadRequest() throws Exception {
    var request = CreateVehicleRequest.builder()
        .year("2222222")
        .build();

    assertThat(customerRepository.existsById(customer.getId())).isTrue();

    mockMvc.perform(
        post("/api/customers/" + customer.getId() + "/vehicles")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isBadRequest()
    ).andDo(result -> {
      WebResponse<Void, List<String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });
      System.out.println(objectMapper.writeValueAsString(response));
      assertThat(response.getErrors()).isNotEmpty();
      assertThat(response.getData()).isNull();

    });
  }
  @Test
  void createSuccess() throws Exception {
    var request = CreateVehicleRequest.builder()
        .licensePlate("F 0011 PP")
        .brand("Suzuki")
        .model("Carry")
        .color("Blue Yellow")
        .year("1020")
        .build();

    assertThat(customerRepository.existsById(customer.getId())).isTrue();

    mockMvc.perform(
        post("/api/customers/" + customer.getId() + "/vehicles")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isCreated()
    ).andDo(result -> {
      WebResponse<VehicleResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(vehicleRepository.existsById(response.getData().getId())).isTrue();
      long count = vehicleRepository.findAllByCustomer_Id(customer.getId()).size();
      assertThat(count).isEqualTo(2);

      assertThat(response.getErrors()).isNull();
      assertThat(response.getData()).isNotNull();
      assertThat(response.getData().getBrand()).isEqualTo("Suzuki");

    });
  }
}