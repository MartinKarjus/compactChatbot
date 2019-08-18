package db.repository;

import objects.dbentities.PlanAccomplished;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanAccomplishedRepository extends JpaRepository<PlanAccomplished, Long> {
}
