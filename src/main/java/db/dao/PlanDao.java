package db.dao;


import objects.dbentities.Plan;
import objects.dbentities.PlanAccomplished;
import objects.dbentities.QuestionGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PlanDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<PlanAccomplished> getAccomplishedPlans() {
        return entityManager.createQuery("select pa from PlanAccomplished pa", PlanAccomplished.class).getResultList();
    }

    public List<Plan> getPlanForDay(List<Long> daysForUsergroups, List<QuestionGroup> questionGroups) {
        List<Long> ids = questionGroups
                .stream()
                .map(QuestionGroup::getId)
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

    public List<Plan> getPlans() {
        return
                entityManager.createQuery(
                        "select u from Plan u", Plan.class)
                        .getResultList();
    }

    public List<PlanAccomplished> getPlanAccomplished() {
        return
                entityManager.createQuery(
                        "select u from PlanAccomplished u", PlanAccomplished.class)
                        .getResultList();
    }

    public Map<Long, List<Long>> getPlanAccomplishedToUserById() {
        HashMap<Long, List<Long>> userIdToPlanIds = new HashMap<>();

        for (PlanAccomplished planAccomplished : getPlanAccomplished()) {
            if(userIdToPlanIds.containsKey(planAccomplished.getUserId())) {
                userIdToPlanIds.get(planAccomplished.getUserId()).add(planAccomplished.getPlanId());
            } else {
                List<Long> planIds = new ArrayList<>();
                planIds.add(planAccomplished.getPlanId());
                userIdToPlanIds.put(planAccomplished.getUserId(), planIds);
            }
        }
        return userIdToPlanIds;
    }

    public HashMap<Long, Plan> getAllPlansById() {
        HashMap<Long, Plan> plansById = new HashMap<>();
        for (Plan plan : getPlans()) {
            plansById.put(plan.getId(), plan);
        }
        return plansById;
    }
}
