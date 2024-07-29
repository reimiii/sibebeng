package franxx.code.sibebeng.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import franxx.code.sibebeng.dto.WebResponse;
import franxx.code.sibebeng.dto.customer.request.CreateCustomerRequest;
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
}