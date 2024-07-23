package franxx.code.sibebeng.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity @Data @Table(name = "repair_details")
public class RepairDetail {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "issue_description")
  private String issueDescription;

  @Column(name = "repair_action")
  private String repairAction;

  private BigDecimal cost;

  @Enumerated(EnumType.STRING)
  private StatusPayment statusPayment = StatusPayment.UNPAID;

  @ManyToOne @JoinColumn(name = "repair_id", referencedColumnName = "id")
  private Repair repair;
}
