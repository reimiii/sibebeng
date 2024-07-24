package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, String> {
}
