package bot.chatfuelapi.forking;


import com.fasterxml.jackson.databind.ObjectMapper;
import db.repository.PlanRepository;
import db.repository.QuestionRepository;
import objects.dbentities.BotUser;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.dbentities.TransmissionLog;
import objects.shared.ContentByPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class NextContentGetter {

    public static final int BASE_WAIT_TIME = 1000;
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AsyncBroadcast asyncBroadcast;

    @Autowired
    private PlanRepository planRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private LinkedHashMap<String, List<Long>> chatfuelIdToForkedLeadToQuestionId = new LinkedHashMap<>(); // < chatfuelId, List<forked question IDs> >

    private Integer getWriteTime(ContentByPlatform contentByPlatform) {

        return 0;
    }

    private ContentByPlatform getContentFromQuestion(Question question) {
        if (question.getText() != null) {
            try {
                return objectMapper.readValue(question.getText(), ContentByPlatform.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read contentByPlatform from question: " + question);
            }
        } else {
            throw new RuntimeException("Invalid question, question has no contentByPlatform(text == null): " + question);
        }
    }
//    private Question firstQuestionIdWithContent(Long leadsToId) {
//        Question q;
//        while(true) {
//            if()
//        }

//    }

    public void broadCastByIdToUser(Long questionId, String userId) {
        Question q = questionRepository.findById(questionId).get();
        asyncBroadcast.broadcastNextQuestion(userId, String.valueOf(q.getId()), 0);
    }


    public void findAndBroadcastNextQuestion(BotUser user, Question sentQuestion, String chatfuelUserId) {
        ContentByPlatform sentQuestionContent = getContentFromQuestion(sentQuestion); //todo results in Null in lead_to, but first If catches all lead_to-s

        //todo at some point i will remake questions that have no content and tie them to it
        // at that point i also need to remake this(currently if a question contains both a Choice and a leadToQuestionName, it sends both at once..
        // possible solutions: marking question as choice, checking what the content is
        System.out.println("newQuestionRequest<sentquestion, chatfueluserid>: \n\t" + sentQuestion + "\n\t, " + chatfuelUserId);
        if (sentQuestion.getLeadsToQuestionName() != null) { // lead_to
            List<Plan> plans = planRepository.findByNamedMessage(sentQuestion.getLeadsToQuestionName());
            if (plans.size() == 0) {
                throw new RuntimeException("Question leads to named question that doesn't exist: " + sentQuestion);
            } else {
                Plan p = plans.get(0); //todo replace this with random choice(or whatever other logic we come up with)
                // some plans share same name, such as greetings(not implemented in content yet so not implementing the logic to support them yet..)

                asyncBroadcast.broadcastNextQuestion(chatfuelUserId, String.valueOf(p.getQuestionId()), 3000);
            }

            addNextQuestionToQueue(sentQuestion, chatfuelUserId);

        } else if (sentQuestionContent.isFork() && sentQuestion.getLeadsToQuestionId() != null) { // choices
            addNextQuestionToQueue(sentQuestion, chatfuelUserId);
        } else if (!sentQuestionContent.isFork() && sentQuestion.getLeadsToQuestionId() != null) { // normal content
            asyncBroadcast.broadcastNextQuestion(chatfuelUserId, String.valueOf(sentQuestion.getLeadsToQuestionId()), 3000);
        } else if (sentQuestion.getLeadsToQuestionId() == null) {
            if(chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId) != null) {
                if(chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId).size() > 0) {

                    Long id = chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId)
                            .get(chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId).size() - 1);

                    chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId)
                            .remove(chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId).size() - 1);

                    asyncBroadcast.broadcastNextQuestion(chatfuelUserId, String.valueOf(id), 3000);
                }

                if(chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId).size() == 0) {
                    chatfuelIdToForkedLeadToQuestionId.remove(chatfuelUserId);
                }
            }
        }


    }


    private void addNextQuestionToQueue(Question sentQuestion, String chatfuelUserId) {
        if (chatfuelIdToForkedLeadToQuestionId.containsKey(chatfuelUserId)) {
            chatfuelIdToForkedLeadToQuestionId.get(chatfuelUserId).add(sentQuestion.getLeadsToQuestionId());
        } else {
            List<Long> questionsToSendLaterIds = new ArrayList<>();
            questionsToSendLaterIds.add(sentQuestion.getLeadsToQuestionId());
            chatfuelIdToForkedLeadToQuestionId.put(chatfuelUserId, questionsToSendLaterIds);
        }
    }


}
