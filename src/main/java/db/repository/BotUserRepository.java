package db.repository;

import objects.dbentities.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {


}
