package franxx.code.sibebeng.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data @Table(name = "customers")
public class Customer {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String name;
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(unique = true)
  private String email;
}
