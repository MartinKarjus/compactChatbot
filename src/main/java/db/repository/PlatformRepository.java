package db.repository;

import objects.dbentities.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Platform findByName(String name);
}
