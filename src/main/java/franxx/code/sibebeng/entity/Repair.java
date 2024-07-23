package franxx.code.sibebeng.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity @Data @Table(name = "repairs")
public class Repair {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "entry_date")
  private LocalDateTime entryDate;

  @Column(name = "exit_date")
  private LocalDateTime exitDate;

  private String description;

  @ManyToOne @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
  private Vehicle vehicle;

}
