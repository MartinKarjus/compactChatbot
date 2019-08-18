package db.repository;

import objects.dbentities.TransmissionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionLogRepository extends JpaRepository<TransmissionLog, Long> {
}
