package bot;

import dao.ActionPlanDao;
import objects.ActionPlan;
import order.OrderJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class BotController {

    @Autowired
    ActionPlanDao actionPlanDao;

    //@Scheduled(fixedRate = 60000)
//    @Scheduled(fixedRate = 60000)
//    public void scheduleFixedRateTask() {
//        System.out.println("checking questions");
//        checkActionPlan();
//    }


    @PersistenceContext
    private EntityManager entityManager;




    private void checkActionPlan() {
        System.out.println(actionPlanDao.getActionPlan());
    }
}
