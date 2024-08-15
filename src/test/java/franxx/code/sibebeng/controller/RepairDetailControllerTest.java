package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.repairdetail.request.CreateRepairDetailRequest;
import franxx.code.sibebeng.dto.repairdetail.request.UpdateRepairDetailRequest;
import franxx.code.sibebeng.dto.repairdetail.response.RepairDetailResponse;
import franxx.code.sibebeng.entity.*;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RepairDetailControllerTest {
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

  private RepairDetail repairDetail;

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

    repairDetail = new RepairDetail();
    repairDetail.setRepair(repair);
    repairDetail.setIssueDescription("Mesin Panas");
    repairDetail.setRepairAction("ganti paking head");
    repairDetail.setPrice(6_000_000L);
    repairDetailRepository.save(repairDetail);
  }

  @AfterEach
  void tearDown() {
    repairDetailRepository.deleteAll();
    repairRepository.deleteAll();
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();
  }

  @Test
  void createRepairDetailSuccess() throws Exception {
    var request = CreateRepairDetailRequest.builder()
        .repairAction("Ganti oli")
        .issueDescription("Oli bocor")
        .price(150000L)
        .build();

    mockMvc.perform(post("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId() + "/details")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andDo(result -> {
          WebResponse<RepairDetailResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(repairDetailRepository.existsById(response.getData().getId())).isTrue();

          assertThat(response.getData().getRepairAction()).isEqualTo("Ganti oli");
          assertThat(response.getData().getIssueDescription()).isEqualTo("Oli bocor");
          assertThat(response.getData().getPrice()).isEqualTo(150000L);

          var savedRepairDetail = repairDetailRepository.findById(response.getData().getId()).orElseThrow();
          assertThat(savedRepairDetail.getStatusPayment()).isEqualTo(StatusPayment.UNPAID);
        });
  }

  @Test
  void createRepairDetailBadRequest() throws Exception {
    // Skenario 1: issueDescription kosong
    var request1 = CreateRepairDetailRequest.builder()
        .repairAction("Ganti oli")
        .issueDescription("") // invalid
        .price(150000L)
        .build();

    mockMvc.perform(post("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId() + "/details")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request1)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").exists()) // cek ada field 'errors' di response
        .andExpect(jsonPath("$.errors.issueDescription").value("must not be blank"))
        .andDo(result -> {
          WebResponse<?, ?> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
        });


    // Skenario 2: price negatif
    var request2 = CreateRepairDetailRequest.builder()
        .repairAction("Ganti oli")
        .issueDescription("Oli bocor")
        .price(-1000L) // invalid
        .build();

    mockMvc.perform(post("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId() + "/details")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request2)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").exists())
        .andExpect(jsonPath("$.errors.price").value("must be greater than or equal to 0"))
        .andDo(result -> {
          WebResponse<?, ?> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
        });

    // Skenario 3: status tidak valid
    var request3 = CreateRepairDetailRequest.builder()
        .repairAction("Ganti oli")
        .issueDescription("Oli bocor")
        .status("INVALID_STATUS") // invalid
        .price(150000L)
        .build();

    mockMvc.perform(post("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId() + "/details")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request3)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").exists())
        .andExpect(jsonPath("$.errors.status").value("Invalid status payment"))
        .andDo(result -> {
          WebResponse<?, ?> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
        });
  }

  @Test
  void updateRepairDetailSuccess() throws Exception {

    assertThat(repairDetailRepository.existsById(repairDetail.getId())).isTrue();

    var request = UpdateRepairDetailRequest.builder()
        .repairAction("Ganti Ban Belakang")
        .price(250000L)
        .status("PAID")
        .build();

    mockMvc.perform(patch("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId() + "/details/" + repairDetail.getId())
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(result -> {
          WebResponse<RepairDetailResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));

          assertThat(repairDetailRepository.existsById(response.getData().getId())).isTrue();
          assertThat(response.getData().getRepairAction()).isEqualTo("Ganti Ban Belakang");
          assertThat(response.getData().getPrice()).isEqualTo(250000L);
        });
  }

  @Test
  void deleteRepairDetailSuccess() throws Exception {
    // Setup initial RepairDetail
    var rd = new RepairDetail();
    rd.setRepair(repair);
    rd.setRepairAction("Ganti Filter");
    rd.setIssueDescription("Filter kotor");
    rd.setPrice(50000L);
    repairDetailRepository.save(rd);

    assertThat(repairDetailRepository.existsById(rd.getId())).isTrue();

    mockMvc.perform(delete("/api/customers/" + customer.getId() + "/vehicles/" + vehicle.getId() + "/repairs/" + repair.getId() + "/details/" + rd.getId())
            .accept(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(result -> {
          WebResponse<String, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));

          assertThat(response.getMessage()).isEqualTo("repair detail deleted successfully");
          assertThat(response.getData()).isEqualTo("OK");
          assertThat(repairDetailRepository.existsById(rd.getId())).isFalse();
        });
  }

}