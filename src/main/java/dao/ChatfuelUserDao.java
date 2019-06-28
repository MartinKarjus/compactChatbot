package dao;

import objects.ActionPlan;
import objects.ChatfuelUser;
import objects.Person;
import objects.QuestionGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class ChatfuelUserDao {




    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getChatFuelUsers() {
        entityManager.getTransaction().begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        //Root<Person> from = cq.from(Person.class);
        //Join<Person, ChatfuelUser> join = from.join("personId", JoinType.LEFT);
        //cq.where(cb.equal(join.get("id"), join.get("personId")));
        //TypedQuery<Person> tq = entityManager.createQuery(cq);

        TypedQuery<Person> tq =
                entityManager.createQuery("SELECT p FROM Person p, ChatfuelUser c WHERE p.id = c.personId", Person.class);

        List<Person> persons = tq.getResultList();
        for (Person p : persons) {
            System.out.println(p.getId());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        return persons;
        //return entityManager.createQuery("select o from Person o where Person", Person.class).getResultList();
    }

    public List<QuestionGroup> getQuestionGroups() {
        return null;
    }
}
