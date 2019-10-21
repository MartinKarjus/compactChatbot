package bot.chatfuelapi;


import bot.chatfuelapi.forking.NextContentGetter;
import bot.update.ContentUpdater;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.repository.PlanAccomplishedRepository;
import db.repository.TransmissionLogRepository;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.*;
import objects.shared.ContentByPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatfuelContentSender {
    // TODO add check for no chatfuel content / write time, if neither exist throw error, otherwise use write time first(we dont actually ever get both at once i think?)
    // todo add lead_to planning logic(save original plan to map, when lead to ends resume original?)

    //todo get write/wait time from the NEXT block

    @Autowired
    private ContentUpdater contentUpdater;

    @Autowired
    private ChatfuelBroadcaster broadcaster;

    @Autowired
    private Environment env;

    @Autowired
    private PlanAccomplishedRepository planAccomplishedRepository;

    @Autowired
    private TransmissionLogRepository transmissionLogRepository;

    @Autowired
    private NextContentGetter nextContentGetter;


    private ObjectMapper objectMapper = new ObjectMapper();


    private ContentByPlatform getContentByPlatform(String json) throws IOException {
        ContentByPlatform contentByPlatform = objectMapper.readValue(json, ContentByPlatform.class);

        if (contentByPlatform.getChatfuelResponse() == null) {
            throw new RuntimeException("contentByPlatform has no chatfuel response in it");
        } else {
            return contentByPlatform;
        }
    }


    public void broadcastToMakeChatfuelRequestContent(Platform platform, Plan plan, BotUser user, PlatformToUser platformToUser) {
        broadcaster.broadcastBlockToUser(platformToUser.getPlatformSpecificData(), env.getProperty("chatfuel_update_block"), null);
    }



    private ChatfuelResponse getContentAndScheduleFollowup(BotUser user, Long questionId, boolean isFollowup, String chatfuelUserId) {
        Question question = contentUpdater.getQuestionsById().get(questionId);
        ContentByPlatform contentByPlatform = null;

        try {
            contentByPlatform = getContentByPlatform(question.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TransmissionLog t = new TransmissionLog();
        t.setQuestionId(questionId);
        t.setUserId(user.getId());
        transmissionLogRepository.save(t);

        nextContentGetter.findAndBroadcastNextQuestion(user, question, chatfuelUserId);//todo move to seperate thread maybe

        return contentByPlatform.getChatfuelResponse();
    }


    public ChatfuelResponse getChatfuelContent(Platform platform, Plan plan, BotUser user, PlatformToUser platformToUser) {
        PlanAccomplished planAccomplished = new PlanAccomplished();
        planAccomplished.setPlanId(plan.getId());
        planAccomplished.setUserId(user.getId());
        planAccomplishedRepository.save(planAccomplished);


        return getContentAndScheduleFollowup(user, plan.getQuestionId(), false, platformToUser.getPlatformSpecificData());
    }


    public ChatfuelResponse getChatfuelContentByQuestionId(BotUser user, Long questionId, String chatfuelUserId) {
        return getContentAndScheduleFollowup(user, questionId, true, chatfuelUserId);
    }
}
