package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.PageableData;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
import franxx.code.sibebeng.dto.customer.request.UpdateCustomerRequest;
import franxx.code.sibebeng.dto.customer.response.CustomerResponse;
import franxx.code.sibebeng.dto.customer.response.SimpleCustomerResponse;
import franxx.code.sibebeng.entity.Customer;
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

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest @AutoConfigureMockMvc class CustomerControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private CustomerRepository customerRepository;

  @Autowired private VehicleRepository vehicleRepository;

  @Autowired private RepairRepository repairRepository;

  @Autowired private RepairDetailRepository repairDetailRepository;

  private Customer customer;

  private Vehicle vehicle;

  @BeforeEach
  void setUp() {
    repairDetailRepository.deleteAll();
    repairRepository.deleteAll();
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();

    objectMapper = objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

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
    repairDetailRepository.deleteAll();
    repairRepository.deleteAll();
    vehicleRepository.deleteAll();
    customerRepository.deleteAll();
  }

  @Test
  void createdBadRequest() throws Exception {
    var request = CreateCustomerRequest.builder().name("mee").build();

    mockMvc.perform(post("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isBadRequest())
        .andDo(result -> {
          WebResponse<Void, Map<String, String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNotNull();
          assertThat(response.getData()).isNull();
        });
  }

  @Test
  void createdSuccess() throws Exception {
    var request = CreateCustomerRequest.builder()
        .name("mee")
        .email("me@mail.com")
        .phoneNumber("05155341230")
        .address("Bogor")
        .build();

    mockMvc.perform(post("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isCreated())
        .andDo(result -> {
          WebResponse<SimpleCustomerResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNull();
          assertThat(customerRepository.existsById(response.getData().getId())).isTrue();
          assertThat(response.getData().getName()).isEqualTo("mee");
        });
  }

  @Test
  void getNotFound() throws Exception {
    mockMvc.perform(get("/api/customers/23123123123")
            .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(status().isNotFound())
        .andDo(result -> {
          WebResponse<Void, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNotNull();
          assertThat(response.getData()).isNull();
        });
  }

  @Test
  void getSuccess() throws Exception {

    assertThat(customerRepository.existsById(customer.getId())).isTrue();

    mockMvc.perform(get("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<CustomerResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNull();
          assertThat(response.getData()).isNotNull();
          assertThat(response.getData().getAddress()).isEqualTo("BGR");
          assertThat(customerRepository.existsById(response.getData().getId())).isTrue();
        });
  }

  @Test
  void updateBadRequest() throws Exception {
    var request = UpdateCustomerRequest.builder().name("mee").email("salah").build();

    assertThat(customerRepository.existsById(customer.getId())).isTrue();

    mockMvc.perform(put("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isBadRequest())
        .andDo(result -> {
          WebResponse<Void, Map<String, String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNotNull();
          assertThat(response.getData()).isNull();
        });
  }

  @Test
  void updateSuccess() throws Exception {
    var request = UpdateCustomerRequest.builder()
        .id("mwehehee") // ini hanya untuk test, tidak akan disimpan ke database
        .name("Mee")
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .address("JKT")
        .build();

    System.out.println(objectMapper.writeValueAsString(request));

    mockMvc.perform(put("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<SimpleCustomerResponse, List<String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNull();
          assertThat(response.getData()).isNotNull();
          assertThat(customerRepository.existsById(response.getData().getId())).isTrue();
        });
  }

  @Test
  void deleteCustomerWithVehiclesShouldFail() throws Exception {
    // Pre-condition
    assertThat(customerRepository.existsById(customer.getId())).isTrue();

    mockMvc.perform(delete("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(status().isConflict())
        .andDo(result -> {
          WebResponse<Void, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNotNull();

          // Post-condition
          assertThat(customerRepository.existsById(customer.getId())).isTrue();
        });
  }

  @Test
  void deletedNotFound() throws Exception {
    assertThat(customerRepository.existsById("not-found")).isFalse();

    mockMvc.perform(delete("/api/customers/not-found")
            .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(status().isNotFound())
        .andDo(result -> {
          WebResponse<Void, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNotNull();
          assertThat(response.getData()).isNull();
          assertThat(response.getMessage()).isEqualTo("customer not found");
          assertThat(response.getMessage()).isNotNull();
        });
  }

  @Test
  void deleteCustomerWithoutVehiclesShouldSucceed() throws Exception {
    // Kosongkan kendaraan dari customer
    vehicleRepository.deleteAll();

    // Pre-condition
    assertThat(customerRepository.existsById(customer.getId())).isTrue();

    mockMvc.perform(delete("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<String, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getErrors()).isNull();
          assertThat(response.getData()).isEqualTo("OK");
          assertThat(response.getMessage()).isNotNull();

          // Post-condition
          assertThat(customerRepository.existsById(customer.getId())).isFalse();
        });
  }

  @Test
  void searchWithPaging() throws Exception {
    for (int i = 0; i < 50; i++) {
      var customer = new Customer();
      customer.setName("Test name: " + i);
      customer.setEmail("main@mail" + i + ".com");
      customer.setPhoneNumber("09876543100" + i);
      customer.setAddress("Address : " + i);
      customerRepository.save(customer);
    }

    mockMvc.perform(get("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .param("page", "1")
            .param("size", "10"))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<PageableData<SimpleCustomerResponse>, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getData().getContent()).isNotNull();
          assertThat(response.getData().getContent()).hasSize(10);
        });

    System.out.println("kosong ga pake param");
    mockMvc.perform(get("/api/customers")
            .accept(MediaType.APPLICATION_JSON))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<PageableData<SimpleCustomerResponse>, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getData().getContent()).isNotNull();
          assertThat(response.getData().getContent()).hasSize(5);
        });

    System.out.println("search aja");
    mockMvc.perform(get("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .param("keyword", "28"))
        .andExpectAll(status().isOk())
        .andDo(result -> {
          WebResponse<PageableData<SimpleCustomerResponse>, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
          System.out.println(objectMapper.writeValueAsString(response));
          assertThat(response.getData().getContent()).isNotNull();
          assertThat(response.getData().getContent()).hasSize(1);
        });
  }
}