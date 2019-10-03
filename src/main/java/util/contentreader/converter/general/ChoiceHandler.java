package util.contentreader.converter.general;

import db.repository.CompanyRepository;
import db.repository.PlanRepository;
import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;
import util.contentreader.converter.chatfuel.ChatfuelConverter;
import util.contentreader.dataclasses.Choice;
import util.contentreader.dataclasses.ContentBase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChoiceHandler {
    private ChatfuelConverter chatfuelConverter = new ChatfuelConverter();
    private QuestionRepository questionRepository;
    private TimeToSendRepository timeToSendRepository;
    private QuestionHandler questionHandler;

//    public void handleChoice(Choice choice, HashMap<Plan, HashMap<Question, ContentByPlatform>> planToQuestionToContent) {

    public ChoiceHandler(QuestionRepository questionRepository, TimeToSendRepository timeToSendRepository, QuestionHandler questionHandler) {
        this.questionRepository = questionRepository;
        this.timeToSendRepository = timeToSendRepository;
        this.questionHandler = questionHandler;
    }

    public void handleChoice(Choice choice,
                             QuestionRepository questionRepository,
                             TimeToSendRepository timeToSendRepository,
                             CompanyRepository companyRepository,
                             PlanRepository planRepository) {
        HashMap<Integer, HashMap<Question, ContentByPlatform>> answerIdToQuestion = new LinkedHashMap<>();

        //Map.Entry<Question, ContentByPlatform> previousQuestion = getLastQuestionWithId(questionContentByPlatformMap);
        //currentQuestion.getValue().setFork(true);
        //questionContentByPlatformMap.get(currentQuestion).setFork(true);
        questionHandler.getContentForCurrentQuestion().setFork(true);

        for (int i = 1; i <= choice.getAnswers().keySet().size(); i++) {
            //HashMap<Question, ContentByPlatform> map = new HashMap<>();

            //GeneralConverter converter = new GeneralConverter(this.questionRepository, this.timeToSendRepository);
            GeneralConverter converter = new GeneralConverter(questionRepository, timeToSendRepository, companyRepository, planRepository);

            converter.convertContent(choice.getAnswers().get(i));
//            for (ContentBase base : choice.getAnswers().get(i)) {
//
//                if (converter.getQuestionContentByPlatformMap().size() > 0) {
//                    Map.Entry<Question, ContentByPlatform> lastQuestion = getLastQuestion(converter.getQuestionContentByPlatformMap());
//                    map = converter.convertContentBase(base, lastQuestion.getKey());
//                } else {
//                    Question newQuestion = new Question();
//                    converter.getQuestionContentByPlatformMap().put(newQuestion, new ContentByPlatform());
//                    map = converter.convertContentBase(base, newQuestion);
//                }
//            }
            //answerIdToQuestion.put(i, map);
            converter.getQuestionHandler().setCurrentQuestionAsFinal();
            answerIdToQuestion.put(i, converter.getQuestionHandler().getQuestionContentByPlatformMap());

//            for (Map.Entry<Question, ContentByPlatform> entry : converter.getQuestionContentByPlatformMap().entrySet()) { // i wonder what this was for..
//                questionContentByPlatformMap.put(entry.getKey(), entry.getValue());
//            }

        }
        //chatfuelConverter.addChoice(questionContentByPlatformMap.get(question).getChatfuelResponse(), choice, answerIdToQuestion, currentQuestion.getKey().getId());

        questionHandler.makeNextQuestion();

        chatfuelConverter.addChoice(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(), choice, answerIdToQuestion, questionHandler.getLastQuestion().getId());
        questionHandler.saveQuestion(questionHandler.getLastQuestion()); // mm, might want to rework this, it's an overlycomplicated solution to a simple problem
        // reason: if we want to get id for the CURRENT question, we would have to save it at creation and AGAIN when we move onto next question(to save changes)
        // this cuts back on DB modifications needed and suprisingly, alot on the time needed to run the contentparser



    }
}
