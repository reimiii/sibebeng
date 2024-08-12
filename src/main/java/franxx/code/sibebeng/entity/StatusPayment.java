package franxx.code.sibebeng.entity;

import lombok.Getter;

@Getter public enum StatusPayment {
  PAID("PAID"),
  UNPAID("UNPAID"),
  CANCELED("CANCELED");

  private final String description;

  StatusPayment(String description) {
    this.description = description;
  }

}
