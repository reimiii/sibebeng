package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.Repair;
import franxx.code.sibebeng.entity.RepairDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairDetailRepository extends JpaRepository<RepairDetail, String> {

  Optional<RepairDetail> findByRepairAndId(Repair repair, String repairDetailId);
}
