package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.repair.request.CreateRepairRequest;
import franxx.code.sibebeng.dto.repair.request.UpdateRepairRequest;
import franxx.code.sibebeng.dto.repair.response.SimpleRepairResponse;
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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RepairControllerTest {

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
    repair.setDescription("Engine Repair Test");
    repair.setEntryDate(LocalDateTime.parse("2024-08-11T08:00:00"));
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

    var request = CreateRepairRequest.builder()
        .entryDate("2024-08-11T09:46:40")
        .build();

    mockMvc.perform(post("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isBadRequest())
        .andDo(result -> {
          WebResponse<Void, Map<String, String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).containsKeys("description");
        });

  }

  @Test
  void createSuccess() throws Exception {
    // check if this string dateTime valid
    String validDateTime = "2024-08-11T09:46:40";

    assertDoesNotThrow(() -> {
      LocalDateTime parsedDateTime = LocalDateTime.parse(validDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      System.out.println(parsedDateTime);
      assertThat(parsedDateTime).isInstanceOf(LocalDateTime.class);
    });

    var request = CreateRepairRequest.builder()
        .entryDate(validDateTime)
        .description("Nge cek mesin")
        .build();

    mockMvc.perform(post("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isCreated())
        .andDo(result -> {
          WebResponse<SimpleRepairResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));

          assertThat(repairRepository.existsById(response.getData().getId())).isTrue();
          assertThat(response.getData().getExitDate()).isNull();
          assertThat(response.getData().getEntryDate()).isInstanceOf(String.class);
        });

  }

  @Test
  void updateRepairInvalidDateFormat() throws Exception {
    var request = UpdateRepairRequest.builder()
        .entryDate("2024-08-11T09:46:4")
        .exitDate("invalid-date-format")
        .description("Service machine repair")
        .build();

    mockMvc.perform(patch("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId())
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isBadRequest())
        .andDo(result -> {
          WebResponse<Void, Map<String, String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).containsKey("exitDate");
        });
  }

  @Test
  void updatePartialSuccess() throws Exception {
    var validExitDate = "2024-08-12T08:00:00";

    var request = UpdateRepairRequest.builder()
        .exitDate(validExitDate)
        .build();

    mockMvc.perform(patch("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId())
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<SimpleRepairResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));

          // Assert that the changes were applied correctly
          var updatedRepair = repairRepository.findById(repair.getId()).orElseThrow();
          assertThat(updatedRepair.getExitDate()).isEqualTo(LocalDateTime.parse(validExitDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
          assertThat(response.getData().getExitDate()).isEqualTo(validExitDate);
        });
  }

}