package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
import franxx.code.sibebeng.dto.customer.request.UpdateCustomerRequest;
import franxx.code.sibebeng.dto.customer.response.CustomerResponse;
import franxx.code.sibebeng.entity.Customer;
import franxx.code.sibebeng.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CustomerRepository customerRepository;

  private Customer customer;

  @BeforeEach
  void setUp() {
    customerRepository.deleteAll();

    objectMapper = objectMapper
        .configure(SerializationFeature.INDENT_OUTPUT, true);

    customer = new Customer();
    customer.setName("Hilmi AM");
    customer.setEmail("mail@mail.com");
    customer.setPhoneNumber("0123456789123");
    customer.setAddress("BGR");
    customerRepository.save(customer);
  }

  @Test
  void createdBadRequest() throws Exception {

    var request = CreateCustomerRequest.builder()
        .name("mee")
        .build();

    mockMvc.perform(
        post("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isBadRequest()
    ).andDo(result -> {

      WebResponse<Void, List<String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });

      System.out.println(objectMapper.writeValueAsString(response));
      assertNotNull(response.getErrors());
      assertNull(response.getData());

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

    mockMvc.perform(
        post("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isCreated()
    ).andDo(result -> {

      WebResponse<CustomerResponse, Void> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });

      System.out.println(objectMapper.writeValueAsString(response));
      assertNull(response.getErrors());
      assertTrue(customerRepository.existsById(response.getData().getId()));
      assertEquals("mee", response.getData().getName());

    });

  }

  @Test
  void getNotFound() throws Exception {
    mockMvc.perform(
        get("/api/customers/23123123123")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isNotFound()
    ).andDo(result -> {
      WebResponse<Void, String> response = objectMapper
          .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
          });

      System.out.println(objectMapper.writeValueAsString(response));

      assertNotNull(response.getErrors());
      assertNull(response.getData());
    });

  }

  @Test
  void getSuccess() throws Exception {
    mockMvc.perform(
        get("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {
      WebResponse<CustomerResponse, Void> response = objectMapper
          .readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
          });

      System.out.println(objectMapper.writeValueAsString(response));

      assertNull(response.getErrors());
      assertNotNull(response.getData());

      assertEquals("BGR", response.getData().getAddress());
    });

  }

  @Test
  void updateBadRequest() throws Exception {

    var request = UpdateCustomerRequest.builder()
        .name("mee")
        .email("salah")
        .build();

    mockMvc.perform(
        put("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isBadRequest()
    ).andDo(result -> {

      WebResponse<CustomerResponse, List<String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });

      System.out.println(objectMapper.writeValueAsString(response));
      assertNotNull(response.getErrors());
      assertNull(response.getData());

    });

  }

  @Test
  void updateSuccess() throws Exception {

    var request = UpdateCustomerRequest.builder()
        .id("mwehehee") // oke ga masuk ke db
        .name("Mee")
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .address("JKT")
        .build();

    System.out.println(objectMapper.writeValueAsString(request));

    mockMvc.perform(
        put("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {

      WebResponse<CustomerResponse, List<String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });

      System.out.println(objectMapper.writeValueAsString(response));
      assertNull(response.getErrors());
      assertNotNull(response.getData());

    });

  }

  @Test
  void deletedNotFound() throws Exception {

    mockMvc.perform(
        delete("/api/customers/not-found")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isNotFound()
    ).andDo(result -> {

      WebResponse<Void, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });

      System.out.println(objectMapper.writeValueAsString(response));
      assertNotNull(response.getErrors());
      assertNull(response.getData());
      assertNotNull(response.getMessage());

    });

  }

  @Test
  void deletedSuccess() throws Exception {

    mockMvc.perform(
        delete("/api/customers/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {

      WebResponse<String, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });

      System.out.println(objectMapper.writeValueAsString(response));
      assertNull(response.getErrors());
      assertEquals("OK", response.getData());
      assertNotNull(response.getMessage());

    });

  }

  @Test
  void searchWithPaging() throws Exception {
    for (int i = 0; i < 50; i++) {
      var customer = new Customer();
      customer.setName("Test name: " + i);
      customer.setEmail("main@mail" + i + ".com");
      customer.setPhoneNumber("1234567891012");
      customer.setAddress("JKT: " + i);
      customerRepository.save(customer);
    }

    mockMvc.perform(
        get("/api/customers")
            .param("keyword", "BGR")
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {
      WebResponse<Map<String, Object>, ?> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });
      System.out.println(objectMapper.writeValueAsString(response));
      Map<String, Object> pageable = (Map<String, Object>) response.getData().get("pageable");
      assertEquals(5, pageable.get("currentSize"));
      assertEquals(1, response.getData().get("totalPages"));
    });

    mockMvc.perform(
        get("/api/customers")
            .accept(MediaType.APPLICATION_JSON)
    ).andExpectAll(
        status().isOk()
    ).andDo(result -> {
      WebResponse<Map<String, Object>, ?> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
      });
      System.out.println(objectMapper.writeValueAsString(response));
    });
  }
}