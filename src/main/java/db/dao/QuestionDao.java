package db.dao;

import objects.dbentities.Question;
import objects.dbentities.QuestionGroup;
import objects.dbentities.BotUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionDao {
    @PersistenceContext
    EntityManager entityManager;


    public List<Question> getContentForDay(List<Long> daysForUsergroups, List<QuestionGroup> usergroups, List<BotUser> botUsers) {
        List<Long> ids = usergroups
                .stream()
                .map(QuestionGroup::getId)
                .collect(Collectors.toList());

        System.out.println("------------");
        System.out.println(daysForUsergroups);
        System.out.println(ids);
        System.out.println("------------");

        TypedQuery<Question> query = entityManager.createQuery(
                "select q from Question q, Plan p, QuestionGroup qg " +
                        "where p.contentId = q.id " +
                        "and p.day in :days " +
                        "and qg.id in :usergroupIds " +
                        "and qg.id = p.usergroupId",
                Question.class
        );
        query.setParameter("days", daysForUsergroups);
        query.setParameter("usergroupIds", ids);

        return query.getResultList();
    }


    public List<Question> getAllQuestions() {
        return
                entityManager.createQuery(
                        "select u from Question u", Question.class)
                        .getResultList();
    }

    public HashMap<Long, Question> getAllQuestionsById() {
        HashMap<Long, Question> questionsById = new HashMap<>();
        for (Question question : getAllQuestions()) {
            questionsById.put(question.getId(), question);
        }
        return questionsById;
    }
}
