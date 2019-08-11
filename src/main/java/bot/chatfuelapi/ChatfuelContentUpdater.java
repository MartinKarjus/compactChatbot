package bot.chatfuelapi;

import dao.ContentDao;
import dao.PlanDao;
import dao.UserDao;
import objects.shared.Plan;
import objects.shared.PlanAccomplished;
import objects.shared.QuestionGroup;
import objects.shared.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatfuelContentUpdater {

    private static final String CHATFUEL_PLATFORM_NAME = "chatfuel";

    @Autowired
    UserDao userDao;

    @Autowired
    ContentDao contentDao;

    @Autowired
    PlanDao planDao;

    @Autowired
    ChatfuelContentMaker chatfuelContentMaker;

    private List<User> chatfuelUsers;
    private List<QuestionGroup> questionGroups;
//    private List<Content> contentForToday;
    private List<Plan> plansForToday;
    private List<PlanAccomplished> accomplishedPlans;
//    private Map<Plan, Content> planToContent;


    // shouldnt need to do it on each update call(once per day?)
//    private void refreshChatfuelUsers() {
//        chatfuelUsers = userDao.getAllUsersFromPlatform(CHATFUEL_PLATFORM_NAME);
//        usergroups = userDao.getUsergroupsByUsers(chatfuelUsers);
//    }

//    private List<Long> getDaysForUsergroups() {
//        List<Long> daysAfterStart = new ArrayList<>();
//        //todo atm date of start is 2019-09-01 in somedata.sql and only content is for day 0(same day), need to insert test data with current date
//        usergroups.forEach(ug -> daysAfterStart.add(ChronoUnit.DAYS.between(usergroups.get(0).getStartDate().toLocalDate(), LocalDate.now())));
//        return daysAfterStart;
//    }

//    //todo remake the whole thing with checking for content to send, this is f'in terrible and inefficient
//    private List<Long> getPlanIds() {
//        return plansForToday
//                .stream()
//                .map(Plan::getId)
//                .collect(Collectors.toList());
//    }

//    private void getContentForDay() {
//        List<Content> allContentForDay = contentDao.getContentForDay(getDaysForUsergroups(), usergroups, chatfuelUsers);
//        System.out.println("content len:" + allContentForDay.size());
//        contentForToday = allContentForDay;
//    }

//    private void getPlanForDay() {
//        plansForToday = planDao.getPlanForDay(getDaysForUsergroups(), usergroups);
//    }

//    private boolean planDoneForUser(User user, Plan plan) {
//        for (PlanAccomplished accomplishedPlan : accomplishedPlans) {
//            if (accomplishedPlan.getPlanId().equals(plan.getId()) &&
//                    accomplishedPlan.getUserId().equals(user.getId())) {
//                return true;
//            }
//        }
//        return false;
//    }

//    private Map<User, List<Plan>> getUnsentContent() {
//        Map<User, List<Plan>> contentToSendToUsers = new HashMap<>();
//
//        for (Plan plan : plansForToday) {
//            for (User user : chatfuelUsers) {
//                if(!planDoneForUser(user, plan)) {
//                    if(contentToSendToUsers.containsKey(user)) {
//                        contentToSendToUsers.get(user).add(plan);
//                    } else {
//                        List<Plan> p = new ArrayList<>();
//                        p.add(plan);
//                        contentToSendToUsers.put(user, p);
//                    }
//                }
//            }
//        }
//
//        return contentToSendToUsers;
//    }

//    private void mapContentToPlan() {
//        planToContent = new HashMap<>();
//        for (Plan plan : plansForToday) {
//            for (Content content : contentForToday) {
//                if(plan.getContentId().equals(content.getId())) {
//                    planToContent.put(plan, content);
//                }
//            }
//        }
//    }

//    private List<QuestionQuery> getQuestionQueriesForQuestion(Question question) {
//        List<QuestionQuery> questionQueries = new ArrayList<>();
//        for (QuestionQuery questionQuery : questionQueries) {
//            if(questionQuery.getQuestionId().equals(question.getId())) {
//                questionQueries.add(questionQuery);
//            }
//        }
//        return questionQueries;
//    }


//    private void sendOutContent() {
//        Map<User, List<Plan>> contentToSendToUsers = getUnsentContent();
//        mapContentToPlan();
//
//        for(Map.Entry<User, List<Plan>> entry : contentToSendToUsers.entrySet()) {
//            chatfuelContentMaker.sendContent(entry.getKey(), entry.getValue());
//        }
//    }

//    public void updateUsers() {
//        accomplishedPlans = planDao.getAccomplishedPlans();
//        refreshChatfuelUsers();
//        getContentForDay();
//        getPlanForDay();
//
//        sendOutContent();
//
//    }


}
