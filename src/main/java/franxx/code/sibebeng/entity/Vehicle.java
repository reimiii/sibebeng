package franxx.code.sibebeng.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data @Table(name = "vehicles")
public class Vehicle {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "license_plate")
  private String licensePlate;
  private String model;
  private String year;
  private String color;

  @ManyToOne @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private Customer customer;
}
