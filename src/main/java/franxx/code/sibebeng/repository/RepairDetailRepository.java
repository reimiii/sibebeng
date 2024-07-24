package franxx.code.sibebeng.repository;

import franxx.code.sibebeng.entity.RepairDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairDetailRepository extends JpaRepository<RepairDetail, String> {
}
