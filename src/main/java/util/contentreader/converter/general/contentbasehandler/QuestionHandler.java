package util.contentreader.converter.general.contentbasehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.repository.QuestionRepository;
import lombok.Data;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class QuestionHandler {
    private Question firstQuestion;
    private Question currentQuestion;
    private Question lastQuestion;
    private Question finalQuestion;
    private QuestionRepository questionRepository;
    private HashMap<Question, ContentByPlatform> questionContentByPlatformMap = new HashMap<>();
    private ObjectMapper mapper = new ObjectMapper();

    public QuestionHandler(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        newQuestionWithContent();
    }

    public ContentByPlatform getContentForCurrentQuestion() {
        return questionContentByPlatformMap.get(currentQuestion);
    }

    public HashMap<Question, ContentByPlatform> getQuestionContentByPlatformMap() {
        return questionContentByPlatformMap;
    }

    public void reset() {
        if (currentQuestion != null) {
            throw new RuntimeException("Trying to reset while currentQuestion not null(not set as final question), current question: " + currentQuestion);
        }
        questionContentByPlatformMap = new LinkedHashMap<>();
        newQuestionWithContent();
        lastQuestion = null;
        firstQuestion = null;
    }

    private void newQuestionWithContent() {
        currentQuestion = new Question();
        questionContentByPlatformMap.put(currentQuestion, new ContentByPlatform());
    }

    private String contentAsJson(ContentByPlatform content) {
        try {
            return mapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Failed to parse content: " + content);
            return null;
        }
    }

    public void makeNextQuestion() {
        currentQuestion.setText(contentAsJson(questionContentByPlatformMap.get(currentQuestion)));
        currentQuestion = questionRepository.save(currentQuestion);
        if (lastQuestion != null) {
            lastQuestion.setLeadsToQuestionId(currentQuestion.getId());
            questionRepository.save(lastQuestion);
        } else {
            firstQuestion = currentQuestion;
        }
        lastQuestion = currentQuestion;

        newQuestionWithContent();
    }

    public void saveQuestion(Question question) { // currently only used for changing the question containing a choice(see ChoiceHandler)
        questionRepository.save(question);
    }

    public void setCurrentQuestionAsFinal() {

        if(questionContentByPlatformMap.get(currentQuestion) == null) { //todo figure out why sometimes last question isnt tied to content and remove this(only seems to happen with choices)
            for (Map.Entry<Question, ContentByPlatform> entry : questionContentByPlatformMap.entrySet()) {
                if(entry.getKey().getId() == null) {
                    currentQuestion.setText(contentAsJson(entry.getValue()));
                }
            }
        } else {
            currentQuestion.setText(contentAsJson(questionContentByPlatformMap.get(currentQuestion)));
        }

        currentQuestion = questionRepository.save(currentQuestion);
        if (lastQuestion != null) {
            lastQuestion.setLeadsToQuestionId(currentQuestion.getId());
            questionRepository.save(lastQuestion);
        } else {
            firstQuestion = currentQuestion;
        }
        finalQuestion = currentQuestion;
        currentQuestion = null; // only for reset check
    }
}
