package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.vehicle.request.CreateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.request.UpdateVehicleRequest;
import franxx.code.sibebeng.dto.vehicle.response.SimpleVehicleResponse;
import franxx.code.sibebeng.dto.vehicle.response.VehicleResponse;
import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.Vehicle;
import franxx.code.sibebeng.repository.CustomerRepository;
import franxx.code.sibebeng.repository.RepairDetailRepository;
import franxx.code.sibebeng.repository.RepairRepository;
import franxx.code.sibebeng.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;

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

  @Autowired
  private RepairRepository repairRepository;

  @Autowired
  private RepairDetailRepository repairDetailRepository;

  private Customer customer;

  private Vehicle vehicle;

  private Repair repair;

  @BeforeEach
  void setUp() {
    repairDetailRepository.deleteAll();
    repairRepository.deleteAll();
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

    repair = new Repair();
    repair.setVehicle(vehicle);
    repair.setDescription("Engine Repair");
    repair.setEntryDate(LocalDateTime.now());
    repairRepository.save(repair);
  }

  @AfterEach
  void tearDown() {
    repairDetailRepository.deleteAll();
    repairRepository.deleteAll();
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
      WebResponse<Void, Map<String, String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
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
      WebResponse<SimpleVehicleResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(vehicleRepository.existsById(response.getData().getId())).isTrue();

      assertThat(response.getErrors()).isNull();
      assertThat(response.getData()).isNotNull();
      assertThat(response.getData().getBrand()).isEqualTo("Suzuki");

    });
  }

  @Test
  void updateSuccess() throws Exception {
    var request = UpdateVehicleRequest.builder()
        .licensePlate("F1122BB")
        .brand("Honda")
        .model("Civic")
        .color("Pacman")
        .year("2004")
        .build();

    assertThat(customerRepository.existsById(customer.getId())).isTrue();
    assertThat(vehicleRepository.existsById(vehicle.getId())).isTrue();

    mockMvc.perform(
        put("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {
      WebResponse<SimpleVehicleResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(vehicleRepository.existsById(response.getData().getId())).isTrue();

      assertThat(response.getErrors()).isNull();
      assertThat(response.getData()).isNotNull();
      assertThat(response.getData().getBrand()).isNotEqualTo(vehicle.getBrand());

    });
  }

  @Test
  void updateBadRequest() throws Exception {
    var request = UpdateVehicleRequest.builder()
        .year("2004")
        .build();

    assertThat(customerRepository.existsById(customer.getId())).isTrue();
    assertThat(vehicleRepository.existsById(vehicle.getId())).isTrue();

    mockMvc.perform(
        put("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isBadRequest()
    ).andDo(result -> {
      WebResponse<?, Map<String, String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(response.getErrors()).isNotNull();
      assertThat(response.getData()).isNull();
      assertThat(response.getErrors()).containsKeys("color", "model", "brand");

    });
  }

  @Test
  void getVehicleNotFound() throws Exception {
    mockMvc.perform(
        get("/api/customers/" + customer.getId() + "/vehicles/invalid-id")
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isNotFound()
    ).andDo(result -> {
      WebResponse<Void, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(response.getData()).isNull();
      assertThat(response.getMessage()).isEqualTo("vehicle not found");
      assertThat(response.getErrors()).isEqualTo("404 NOT_FOUND");
    });
  }

  @Test
  void getVehicleSuccess() throws Exception {
    mockMvc.perform(
        get("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId())
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {
      WebResponse<VehicleResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(response.getData()).isNotNull();
      assertThat(response.getData().getId()).isEqualTo(vehicle.getId());
      assertThat(response.getData().getBrand()).isEqualTo(vehicle.getBrand());
      assertThat(response.getData().getModel()).isEqualTo(vehicle.getModel());
      assertThat(response.getData().getLicensePlate()).isEqualTo(vehicle.getLicensePlate());
      assertThat(response.getData().getYear()).isEqualTo(vehicle.getYear());
      assertThat(response.getData().getColor()).isEqualTo(vehicle.getColor());
    });
  }

  @Test
  void deleteVehicleSuccess() throws Exception {
    assertThat(customerRepository.existsById(customer.getId())).isTrue();
    assertThat(vehicleRepository.existsById(vehicle.getId())).isTrue();

    if (repairRepository.existsById(repair.getId())) {
      repairRepository.delete(repair);
    }

    assertThat(repairRepository.existsById(repair.getId())).isFalse();

    mockMvc.perform(
        delete("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId())
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {
      WebResponse<String, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(response.getData()).isEqualTo("OK");
      assertThat(response.getMessage()).isEqualTo("vehicle deleted successfully");

      assertThat(vehicleRepository.existsById(vehicle.getId())).isFalse();
    });
  }

  @Test
  void deleteVehicleWithRepairsConflict() throws Exception {
    mockMvc.perform(
        delete("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId())
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isConflict()
    ).andDo(result -> {
      WebResponse<Void, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
      System.out.println(objectMapper.writeValueAsString(response));

      assertThat(response.getData()).isNull();
      assertThat(response.getErrors()).isNotNull();
      assertThat(response.getErrors()).isEqualTo("409 CONFLICT");
      // Vehicle should still exist
      assertThat(vehicleRepository.existsById(vehicle.getId())).isTrue();
      assertThat(repairRepository.existsById(repair.getId())).isTrue();
    });
  }

}