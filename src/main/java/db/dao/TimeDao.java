package db.dao;

import objects.dbentities.TimeToSend;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Repository
public class TimeDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<TimeToSend> getAllTimesToSend() {
        return entityManager.createQuery("select timeToSend from TimeToSend timeToSend", TimeToSend.class).getResultList();
    }

    public HashMap<Long, TimeToSend> getAllTimesById() {
        HashMap<Long, TimeToSend> timeById = new HashMap<>();
        for (TimeToSend timeToSend : getAllTimesToSend()) {
            timeById.put(timeToSend.getId(), timeToSend);
        }
        return timeById;
    }

}
