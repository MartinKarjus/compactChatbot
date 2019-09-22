package util.contentreader.dbadjuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.repository.PlanRepository;
import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.contentreader.converter.ConvertResult;
import util.contentreader.converter.TempQuestionRep;
import util.contentreader.converter.TempTimeRep;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContentToDb {

    private ConvertResult convertResult;

    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TimeToSendRepository timeToSendRepository;
    @Autowired
    private PlanRepository planRepository;

    public void setConvertResult(ConvertResult convertResult) {
        this.convertResult = convertResult;
    }

    public void saveContentToQuestions() {

        for (Map.Entry<Plan, HashMap<Question, ContentByPlatform>> entry1 : convertResult.getPlanToQuestionToContent().entrySet()) {
            Question q = questionRepository.findById(entry1.getValue().entrySet().iterator().next().getKey().getId()).get();
            entry1.getKey().setQuestionId(q.getId());
            if(entry1.getKey().getDay() == null) {
                entry1.getKey().setDay(-1L);
            }
            planRepository.save(entry1.getKey());

            for (Map.Entry<Question, ContentByPlatform> entry : entry1.getValue().entrySet()) {
                try {
                    String json = mapper.writeValueAsString(entry.getValue());
                    Question question = questionRepository.findById(entry.getKey().getId()).get();
                    question.setText(json);
                    questionRepository.save(question);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
