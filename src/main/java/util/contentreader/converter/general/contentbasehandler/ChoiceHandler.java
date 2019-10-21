package util.contentreader.converter.general.contentbasehandler;

import db.repository.CompanyRepository;
import db.repository.PlanRepository;
import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;
import util.contentreader.converter.chatfuel.ChatfuelConverter;
import util.contentreader.converter.general.GeneralConverter;
import util.contentreader.dataclasses.Choice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChoiceHandler {
    private ChatfuelConverter chatfuelConverter = new ChatfuelConverter();
    private QuestionHandler questionHandler;


    public ChoiceHandler(QuestionHandler questionHandler) {
        this.questionHandler = questionHandler;
    }

    public void handleChoice(Choice choice,
                             QuestionRepository questionRepository,
                             TimeToSendRepository timeToSendRepository,
                             CompanyRepository companyRepository,
                             PlanRepository planRepository) {

        HashMap<Integer, HashMap<Question, ContentByPlatform>> answerIdToQuestion = new LinkedHashMap<>();

        if(questionHandler.getContentForCurrentQuestion().isFork()) {
            questionHandler.makeNextQuestion();
        }
        questionHandler.getContentForCurrentQuestion().setFork(true);

        for (int i = 1; i <= choice.getAnswers().keySet().size(); i++) {

            GeneralConverter converter = new GeneralConverter(questionRepository, timeToSendRepository, companyRepository, planRepository);

            converter.convertContent(choice.getAnswers().get(i));

            converter.getQuestionHandler().setCurrentQuestionAsFinal();
            answerIdToQuestion.put(i, converter.getQuestionHandler().getQuestionContentByPlatformMap());


        }

        questionHandler.makeNextQuestion();//todo tie last text to question
        System.out.println("- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -");
        System.out.println("answerIdToQuestion: " + answerIdToQuestion);
        for (Map.Entry<Integer, HashMap<Question, ContentByPlatform>> integerHashMapEntry : answerIdToQuestion.entrySet()) {
            System.out.println(integerHashMapEntry);
        }
        System.out.println("- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -");
        System.out.println("contentByPlatformMap: " + questionHandler.getQuestionContentByPlatformMap());
        for (Map.Entry<Question, ContentByPlatform> questionContentByPlatformEntry : questionHandler.getQuestionContentByPlatformMap().entrySet()) {
            System.out.println(questionContentByPlatformEntry);
        }
        System.out.println("- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -");
        System.out.println("last question: " + questionHandler.getLastQuestion());
        System.out.println("content: " + questionHandler.getQuestionContentByPlatformMap().get(questionHandler.getLastQuestion()));
        System.out.println("- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -");

//        chatfuelConverter.addChoice(questionHandler.getQuestionContentByPlatformMap().get(questionHandler.getLastQuestion()).getChatfuelResponse(),
//                choice,
//                answerIdToQuestion,
//                questionHandler.getLastQuestion().getId());
//        questionHandler.saveQuestion(questionHandler.getLastQuestion());
//
//
//
//        questionHandler.makeNextQuestion();

        chatfuelConverter.addChoice(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(),
                choice,
                answerIdToQuestion,
                questionHandler.getLastQuestion().getId());
        questionHandler.saveQuestion(questionHandler.getLastQuestion());



    }
}
