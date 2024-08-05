package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

  Optional<Vehicle> findByCustomer_IdAndId(String customerId, String vehicleId);

  List<Vehicle> findAllByCustomer_Id(String customerId);
}
