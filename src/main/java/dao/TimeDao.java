package dao;

import objects.shared.TimeToSend;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TimeDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<TimeToSend> getAllTimesToSend() {
        return entityManager.createQuery("select timeToSend from TimeToSend timeToSend", TimeToSend.class).getResultList();
    }

//    public List<TimeAsString> getAllTimesAsString() {
//        return entityManager.createQuery("select timeAsString from TimeAsString timeAsString", TimeAsString.class).getResultList();
//    }
}
