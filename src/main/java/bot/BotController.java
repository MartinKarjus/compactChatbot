package bot;


import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class BotController {


    //@Scheduled(fixedRate = 60000)
//    @Scheduled(fixedRate = 60000)
//    public void scheduleFixedRateTask() {
//        System.out.println("checking questions");
//        checkActionPlan();
//    }


    @PersistenceContext
    private EntityManager entityManager;




}
