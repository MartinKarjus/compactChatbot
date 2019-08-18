package bot.update;


import db.dao.PlanDao;
import db.dao.QuestionDao;
import db.dao.TimeDao;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.dbentities.QuestionGroup;
import objects.dbentities.TimeToSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ContentUpdater {

    @Autowired
    UserUpdater userUpdater;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private PlanDao planDao;
    @Autowired
    private TimeDao timeDao;
    private HashMap<Long, Question> questionsById = new HashMap<>();
    private HashMap<Long, Plan> plansById = new HashMap<>();
    private HashMap<Long, TimeToSend> timesById = new HashMap<>();

    private HashMap<Long, List<Plan>> questionGroupIdToCurrentDayPlans = new HashMap<>();

    // gets the current content days groups are at
    private List<Long> getDaysForQuestionGroup() {
        List<Long> daysAfterStart = new ArrayList<>();
        for (QuestionGroup questionGroup : userUpdater.getGroupsById().values()) {
            daysAfterStart.add(ChronoUnit.DAYS.between(questionGroup.getDateCreated().toLocalDateTime().toLocalDate(), LocalDate.now()));
        }
        return daysAfterStart;
    }

    private Long getCurrentDayForQuestionGroup(QuestionGroup questionGroup) {
        return ChronoUnit.DAYS.between(questionGroup.getDateCreated().toLocalDateTime().toLocalDate(), LocalDate.now());
    }

    public List<Plan> getContentForQuestionGroupForDay(Long questionGroupId) {
        if(questionGroupIdToCurrentDayPlans.containsKey(questionGroupId)) {
            return questionGroupIdToCurrentDayPlans.get(questionGroupId);
        }

        QuestionGroup questionGroup = userUpdater.getGroupsById().get(questionGroupId);
        List<Plan> currentDayPlans = new ArrayList<>();
        Long day = getCurrentDayForQuestionGroup(questionGroup);

        //todo check that plan has correct questiongroupId if we add that
        for (Plan plan : plansById.values()) {
            System.out.println("plan day is:" + plan.getDay());
            System.out.println("current day is:" + day);
            if (plan.getDay().equals(day)) {
                currentDayPlans.add(plan);
            }
        }
        questionGroupIdToCurrentDayPlans.put(questionGroupId, currentDayPlans);

        return currentDayPlans;
    }

    public void update() {
        questionsById = questionDao.getAllQuestionsById();
        plansById = planDao.getAllPlansById();
        //plansById = planDao.getAllPlansForDaysById(getDaysForUsergroups());
        timesById = timeDao.getAllTimesById();

    }

    public HashMap<Long, Question> getQuestionsById() {
        return questionsById;
    }

    public HashMap<Long, Plan> getPlansById() {
        return plansById;
    }

    public HashMap<Long, TimeToSend> getTimesById() {
        return timesById;
    }

    public HashMap<Long, List<Plan>> getQuestionGroupIdToCurrentDayPlans() {
        return questionGroupIdToCurrentDayPlans;
    }

    public void setQuestionGroupIdToCurrentDayPlans(HashMap<Long, List<Plan>> questionGroupIdToCurrentDayPlans) {
        this.questionGroupIdToCurrentDayPlans = questionGroupIdToCurrentDayPlans;
    }
}
