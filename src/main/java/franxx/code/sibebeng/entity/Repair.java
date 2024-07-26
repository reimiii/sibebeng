package franxx.code.sibebeng.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @Table(name = "repairs")
public class Repair {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotNull
  @Column(name = "entry_date", nullable = false)
  private LocalDateTime entryDate;

  @Column(name = "exit_date")
  private LocalDateTime exitDate;

  private String description;

  @ManyToOne @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
  private Vehicle vehicle;

  @OneToMany(mappedBy = "repair")
  private List<RepairDetail> repairDetails = new ArrayList<>();

}
