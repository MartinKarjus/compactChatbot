package db.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LoggingDao {
    @PersistenceContext
    EntityManager entityManager;




}
