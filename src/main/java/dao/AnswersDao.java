package dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AnswersDao {

    @PersistenceContext
    EntityManager entityManager;

    public void updateAnswers() {

    }
}
