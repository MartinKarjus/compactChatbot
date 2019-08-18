package db.dao;


import objects.dbentities.QuestionGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionGroupDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<QuestionGroup> getAllQuestionGroups() {
        return
                entityManager.createQuery(
                        "select u from QuestionGroup u", QuestionGroup.class)
                        .getResultList();
    }


    public Map<Long, QuestionGroup> getAllQuestionGroupsById() {
        HashMap<Long, QuestionGroup> idToQuestionGroup = new HashMap<>();
        for (QuestionGroup questionGroup : getAllQuestionGroups()) {
            idToQuestionGroup.put(questionGroup.getId(), questionGroup);
        }
        return idToQuestionGroup;
    }
}
