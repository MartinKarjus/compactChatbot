package bot;

import bot.update.ContentUpdater;
import bot.update.UserUpdater;
import objects.dbentities.BotUser;
import objects.dbentities.Plan;
import objects.dbentities.Platform;
import objects.dbentities.PlatformToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContentManager {

    @Autowired
    private ContentUpdater contentUpdater;

    @Autowired
    private UserUpdater userUpdater;


    // super inefficient, need to come up with a better way
    private List<Plan> filterOutSentContent(BotUser user, List<Plan> userPlans) {
        List<Plan> plansToSend = new ArrayList<>();

        if (!userUpdater.getPlanAccomplishedUserToPlansByIds().keySet().contains(user.getId())) {
            plansToSend.addAll(userPlans);
            return plansToSend;
        }

        for (Plan userPlan : userPlans) {
            if(!userUpdater.getPlanAccomplishedUserToPlansByIds().get(user.getId()).contains(userPlan.getId())) {
                plansToSend.add(userPlan);
            }
        }

        return plansToSend;
    }

    private LocalTime getTimeForPlan(Plan plan) {
        return contentUpdater.getTimesById().get(plan.getTimeToSendId())
                .getTimeToSend().toLocalDateTime().toLocalTime();
    }

    private List<Plan> filterOutLaterContentAndUserGroup(List<Plan> userGroupPlans, BotUser u) {
        List<Plan> plansToSend = new ArrayList<>();

        for (Plan plan : userGroupPlans) {
            if (getTimeForPlan(plan).isBefore(LocalTime.now())) { //todo fix
                    //&& plan.getQuestionGroupId().equals(u.getQuestionGroupId())) {
                plansToSend.add(plan);
            }
        }

        return plansToSend;
    }


    public Map<BotUser, List<Plan>> getUnsentContent() {
        Map<BotUser, List<Plan>> contentToSendToUsers = new HashMap<>();

        for (BotUser user : userUpdater.getUsersById().values()) {
            List<Plan> plans = filterOutSentContent(user, contentUpdater.getContentForQuestionGroupForDay(user.getQuestionGroupId()));
            plans = filterOutLaterContentAndUserGroup(plans, user);

            contentToSendToUsers.put(user, plans);
        }

        return contentToSendToUsers;
    }


    public Plan getLatestToSend(List<Plan> plans) {
        Plan earliest = null;
        for (Plan plan : plans) {
            if (earliest == null) {
                earliest = plan;
            } else if (getTimeForPlan(plan).isBefore(getTimeForPlan(earliest))) {
                earliest = plan;
            }
        }
        plans.remove(earliest);
        //todo save the plans that were skipped for whatever reason(no connection, user didnt exist at start of the day, etc.) into a seperate table
        // transmissions_log table
        return earliest;
    }

    public List<Platform> getPlatformsForUser(BotUser user) {
        List<Platform> platforms = new ArrayList<>();

        for (PlatformToUser platformToUser : userUpdater.getUserToPlatformToUser().get(user.getId())) {
            platforms.add(userUpdater.getPlatformById().get(platformToUser.getPlatformId()));
        }

        return platforms;
    }
}
