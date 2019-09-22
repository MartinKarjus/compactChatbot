package util.contentreader.converter;

import lombok.Data;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
public class ConvertResult {
    private HashMap<Plan, HashMap<Question, ContentByPlatform>> planToQuestionToContent = new LinkedHashMap<>();
    private HashMap<Plan, Question> planToFirstQuestion = new LinkedHashMap<>();

    public ConvertResult(HashMap<Plan, HashMap<Question, ContentByPlatform>> planToQuestionToContent, HashMap<Plan, Question> planToFirstQuestion) {
        this.planToQuestionToContent = planToQuestionToContent;
        this.planToFirstQuestion = planToFirstQuestion;
    }
}
