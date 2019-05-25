package dao;

import objects.ActionPlan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ActionPlanDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ActionPlan> getActionPlan() {
        return entityManager.createQuery("select o from ActionPlan o", ActionPlan.class).getResultList();
    }
}
