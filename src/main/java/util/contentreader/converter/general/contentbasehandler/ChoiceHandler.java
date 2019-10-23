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


        questionHandler.makeNextQuestion();

        questionHandler.getContentForCurrentQuestion().setFork(true);

        for (int i = 1; i <= choice.getAnswers().keySet().size(); i++) {

            GeneralConverter converter = new GeneralConverter(questionRepository, timeToSendRepository, companyRepository, planRepository);

            converter.convertContent(choice.getAnswers().get(i));
            System.out.println();
            System.out.println("converter questions:");
            System.out.println("\t" + converter.getQuestionHandler().getLastQuestion());
            System.out.println("\t" + converter.getQuestionHandler().getCurrentQuestion());

            converter.getQuestionHandler().setCurrentQuestionAsFinal();
            System.out.println("\tfinal: " + converter.getQuestionHandler().getFinalQuestion());
            System.out.println("**");
            System.out.println("question content by platform map:");
            for (Map.Entry<Question, ContentByPlatform> entry : converter.getQuestionHandler().getQuestionContentByPlatformMap().entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
            System.out.println("**");
            answerIdToQuestion.put(i, converter.getQuestionHandler().getQuestionContentByPlatformMap());
            System.out.println();

        }

        System.out.println("answerIdToQuestion: ");
        for (Map.Entry<Integer, HashMap<Question, ContentByPlatform>> integerHashMapEntry : answerIdToQuestion.entrySet()) {
            System.out.println("\tint: " + integerHashMapEntry.getKey());
            System.out.println("\tquestion: " + integerHashMapEntry.getValue());
        }

        chatfuelConverter.addChoice(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(),
                choice,
                answerIdToQuestion,
                questionHandler.getLastQuestion().getId());
        //questionHandler.saveQuestion(questionHandler.getLastQuestion());
        //questionHandler.saveQuestion(questionHandler.getCurrentQuestion());

        questionHandler.makeNextQuestion();

    }
}
