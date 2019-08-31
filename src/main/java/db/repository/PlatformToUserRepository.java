package db.repository;

import objects.dbentities.PlatformToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformToUserRepository extends JpaRepository<PlatformToUser, Long> {
}
