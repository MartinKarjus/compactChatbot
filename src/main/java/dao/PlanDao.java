package dao;


import objects.shared.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PlanDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<PlanAccomplished> getAccomplishedPlans() {
        return entityManager.createQuery("select pa from PlanAccomplished pa", PlanAccomplished.class).getResultList();
    }

    public List<Plan> getPlanForDay(List<Long> daysForUsergroups, List<Usergroup> usergroups) {
        List<Long> ids = usergroups
                .stream()
                .map(Usergroup::getId)
                .collect(Collectors.toList());

        TypedQuery<Plan> query = entityManager.createQuery(
                "select p from Plan p, Usergroup ug " +
                        "where p.day in :days " +
                        "and ug.id in :usergroupIds " +
                        "and ug.id = p.usergroupId",
                Plan.class
        );
        query.setParameter("days", daysForUsergroups);
        query.setParameter("usergroupIds", ids);

        return query.getResultList();


    }

}
