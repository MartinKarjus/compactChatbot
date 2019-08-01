package bot.chatfuelapi;


import dao.QuestionDao;
import dao.TimeDao;
import objects.shared.BotUser;
import objects.shared.Plan;
import objects.shared.TimeAsString;
import objects.shared.TimeToSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChatfuelContentMaker {
    @Autowired
    QuestionDao questionDao;

    @Autowired
    TimeDao timeDao;


    private List<TimeAsString> timesAsStrings = new ArrayList<>();
    private List<TimeToSend> timesToSend = new ArrayList<>();
    private Map<Plan, TimeToSend> planTimeToSend = new HashMap<>();
    private Map<TimeToSend, TimeAsString> timeToSendToTimeAsString = new HashMap<>();


    private void updateTimeToSend() {
        timesToSend = timeDao.getAllTimesToSend();
        timesAsStrings = timeDao.getAllTimesAsString();
    }

    private TimeToSend getTimeToSendById(Long id) {
        for (TimeToSend timeToSend : timesToSend) {
            if(timeToSend.getId().equals(id)) {
                return timeToSend;
            }
        }
        throw new RuntimeException("Plan time does not exist");
    }

    private boolean pastSendingTime() {
        return false;
    }

    private Plan choosePlan(List<Plan> plans) {
        //todo continue
        return null;
    }


    public void sendContent(BotUser user, List<Plan> plans) {
        updateTimeToSend();
    }



}
