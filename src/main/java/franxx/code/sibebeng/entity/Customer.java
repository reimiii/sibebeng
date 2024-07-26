package franxx.code.sibebeng.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "customer")
  private List<Vehicle> vehicles = new ArrayList<>();
}
