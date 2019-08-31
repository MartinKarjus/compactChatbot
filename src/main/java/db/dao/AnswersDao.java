package db.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;

@Repository
public class AnswersDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void save(Long question_id, Timestamp date_created, Long user_id, Long points, String answer) {

        entityManager.createNativeQuery("INSERT INTO public.answers VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, 1)
                .setParameter(2, question_id)
                .setParameter(3, date_created)
                .setParameter(4, user_id)
                .setParameter(5, points)
                .setParameter(6, answer)
                .executeUpdate();
    }
}
