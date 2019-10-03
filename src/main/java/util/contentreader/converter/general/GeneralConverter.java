package util.contentreader.converter.general;

import db.repository.CompanyRepository;
import db.repository.PlanRepository;
import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.dbentities.TimeToSend;
import objects.shared.ContentByPlatform;
import util.contentreader.converter.ConvertResult;
import util.contentreader.converter.chatfuel.ChatfuelConverter;
import util.contentreader.dataclasses.Choice;
import util.contentreader.dataclasses.ContentBase;
import util.contentreader.dataclasses.ContentOptions;
import util.contentreader.dataclasses.SubType;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GeneralConverter {




    private ChatfuelConverter chatfuelConverter = new ChatfuelConverter();


    private QuestionRepository questionRepository;
    private TimeToSendRepository timeToSendRepository;
    private CompanyRepository companyRepository;
    private PlanRepository planRepository;


    private HashMap<Plan, Question> planToFirstQuestion = new LinkedHashMap<>();
    private Plan currentPlan = new Plan();

    private QuestionHandler questionHandler;
    private SubtypeHandler subtypeHandler;
    private OptionHandler optionHandler;
    private ChoiceHandler choiceHandler;



    public GeneralConverter(QuestionRepository questionRepository, TimeToSendRepository timeToSendRepository, CompanyRepository companyRepository, PlanRepository planRepository) {
        this.questionRepository = questionRepository;
        this.timeToSendRepository = timeToSendRepository;
        this.companyRepository = companyRepository;
        this.planRepository = planRepository;

        questionHandler = new QuestionHandler(questionRepository);
        subtypeHandler = new SubtypeHandler(questionHandler);
        optionHandler = new OptionHandler(timeToSendRepository, companyRepository);
        choiceHandler = new ChoiceHandler(questionRepository, timeToSendRepository, questionHandler);
    }



    private void convertContentBase(ContentBase contentBase) {
        //System.out.println("Converting: " + contentBase);
        if (contentBase instanceof Choice) {
            choiceHandler.handleChoice((Choice) contentBase, questionRepository, timeToSendRepository, companyRepository, planRepository);
        } else if (contentBase instanceof ContentOptions) {
           optionHandler.handleOptions((ContentOptions) contentBase, currentPlan);
        } else if (contentBase instanceof SubType) {
            subtypeHandler.handleSubType((SubType) contentBase);
        }
    }



    public void convertContent(List<ContentBase> contentBases) {

        for (ContentBase contentBase : contentBases) {
            System.out.println(contentBase);
            convertContentBase(contentBase);
        }

    }

    private void savePlan() {

    }



    public List<Plan> convertPlans(List<List<List<ContentBase>>> plans) {
        List<Plan> completedPlans = new ArrayList<>();


        for (List<List<ContentBase>> stringPlans : plans) {

            currentPlan = new Plan();

            for (List<ContentBase> contentBases : stringPlans) {

                convertContent(contentBases);
            }
            questionHandler.setCurrentQuestionAsFinal();

            savePlan();
            questionHandler.reset();

            //planToFirstQuestion.put(currentPlan, questionHandler.getFirstQuestion());

            //printCurrentPlan();

//            planToQuestionToContent.put(currentPlan, new LinkedHashMap<>());
//            for (Map.Entry<Question, ContentByPlatform> entry : questionContentByPlatformMap.entrySet()) {
//                planToQuestionToContent.get(currentPlan).put(entry.getKey(), entry.getValue());
//            }

        }

        return completedPlans;
//        return new ConvertResult(planToQuestionToContent, planToFirstQuestion);
    }

    private void printCurrentPlan() {
        System.out.println("--------------------------");
        System.out.println("current plan:" + currentPlan);
        for (Map.Entry<Question, ContentByPlatform> entry : questionHandler.getQuestionContentByPlatformMap().entrySet()) {
            System.out.println("\tquestion: " + entry.getKey());
            System.out.println("\t\tcontent: " + entry.getValue());
        }
        System.out.println("---/--/-/-/--/-/-/-/-/-/-/-/-/---------");
    }

    public QuestionHandler getQuestionHandler() {
        return questionHandler;
    }
}
