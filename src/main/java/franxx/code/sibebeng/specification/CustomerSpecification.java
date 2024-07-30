package franxx.code.sibebeng.specification;

import franxx.code.sibebeng.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

  public static Specification<Customer> containsTextInAttributes(String keyword) {
    return (root, query, criteriaBuilder) -> {
      if (keyword == null || keyword.trim().isEmpty()) {
        return criteriaBuilder.conjunction();
      }

      String likePattern = "%" + keyword.toLowerCase() + "%";
      return criteriaBuilder.or(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
          criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likePattern),
          criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), likePattern),
          criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern)
      );
    };
  }
}
