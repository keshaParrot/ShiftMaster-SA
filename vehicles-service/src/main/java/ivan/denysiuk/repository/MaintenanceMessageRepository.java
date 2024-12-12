package ivan.denysiuk.repository;

import ivan.denysiuk.domain.entity.MaintenanceMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceMessageRepository extends JpaRepository<MaintenanceMessage, Long> {

}
