package db.repository;

import objects.dbentities.TimeToSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeToSendRepository extends JpaRepository<TimeToSend, Long> {
}
