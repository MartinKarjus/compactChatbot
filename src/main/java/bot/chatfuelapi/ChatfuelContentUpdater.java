package bot.chatfuelapi;

import dao.BotUserDao;
import dao.ContentDao;
import dao.PlanDao;
import objects.User;
import objects.shared.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChatfuelContentUpdater {

    private static final String CHATFUEL_PLATFORM_NAME = "chatfuel";

    @Autowired
    BotUserDao botUserDao;

    @Autowired
    ContentDao contentDao;

    @Autowired
    PlanDao planDao;

    @Autowired
    ChatfuelContentMaker chatfuelContentMaker;

    private List<BotUser> chatfuelUsers;
    private List<Usergroup> usergroups;
    private List<Content> contentForToday;
    private List<Plan> plansForToday;
    private List<PlanAccomplished> accomplishedPlans;
    private Map<Plan, Content> planToContent;


    // shouldnt need to do it on each update call(once per day?)
    private void refreshChatfuelUsers() {
        chatfuelUsers = botUserDao.getAllUsersFromPlatform(CHATFUEL_PLATFORM_NAME);
        usergroups = botUserDao.getUsergroupsByUsers(chatfuelUsers);
    }

    private List<Long> getDaysForUsergroups() {
        List<Long> daysAfterStart = new ArrayList<>();
        //todo atm date of start is 2019-09-01 in somedata.sql and only content is for day 0(same day), need to insert test data with current date
        usergroups.forEach(ug -> daysAfterStart.add(ChronoUnit.DAYS.between(usergroups.get(0).getStartDate().toLocalDate(), LocalDate.now())));
        return daysAfterStart;
    }

    //todo remake the whole thing with checking for content to send, this is f'in terrible and inefficient
    private List<Long> getPlanIds() {
        return plansForToday
                .stream()
                .map(Plan::getId)
                .collect(Collectors.toList());
    }

    private void getContentForDay() {
        List<Content> allContentForDay = contentDao.getContentForDay(getDaysForUsergroups(), usergroups, chatfuelUsers);
        System.out.println("content len:" + allContentForDay.size());
        contentForToday = allContentForDay;
    }

    private void getPlanForDay() {
        plansForToday = planDao.getPlanForDay(getDaysForUsergroups(), usergroups);
    }

    private boolean planDoneForUser(BotUser user, Plan plan) {
        for (PlanAccomplished accomplishedPlan : accomplishedPlans) {
            if (accomplishedPlan.getPlanId().equals(plan.getId()) &&
                    accomplishedPlan.getUserId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    private Map<BotUser, List<Plan>> getUnsentContent() {
        Map<BotUser, List<Plan>> contentToSendToUsers = new HashMap<>();

        for (Plan plan : plansForToday) {
            for (BotUser user : chatfuelUsers) {
                if(!planDoneForUser(user, plan)) {
                    if(contentToSendToUsers.containsKey(user)) {
                        contentToSendToUsers.get(user).add(plan);
                    } else {
                        List<Plan> p = new ArrayList<>();
                        p.add(plan);
                        contentToSendToUsers.put(user, p);
                    }
                }
            }
        }

        return contentToSendToUsers;
    }

    private void mapContentToPlan() {
        planToContent = new HashMap<>();
        for (Plan plan : plansForToday) {
            for (Content content : contentForToday) {
                if(plan.getContentId().equals(content.getId())) {
                    planToContent.put(plan, content);
                }
            }
        }
    }

    private List<QuestionQuery> getQuestionQueriesForQuestion(Question question) {
        List<QuestionQuery> questionQueries = new ArrayList<>();
        for (QuestionQuery questionQuery : questionQueries) {
            if(questionQuery.getQuestionId().equals(question.getId())) {
                questionQueries.add(questionQuery);
            }
        }
        return questionQueries;
    }


    private void sendOutContent() {
        Map<BotUser, List<Plan>> contentToSendToUsers = getUnsentContent();
        mapContentToPlan();

        for(Map.Entry<BotUser, List<Plan>> entry : contentToSendToUsers.entrySet()) {
            chatfuelContentMaker.sendContent(entry.getKey(), entry.getValue());
        }
    }

    public void updateUsers() {
        accomplishedPlans = planDao.getAccomplishedPlans();
        refreshChatfuelUsers();
        getContentForDay();
        getPlanForDay();

        sendOutContent();

    }


}
