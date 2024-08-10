package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<Repair, String> {

  Optional<Repair> findByVehicleAndId(Vehicle vehicle, String repairId);
}
