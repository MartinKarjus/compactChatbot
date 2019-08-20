package bot.chatfuelapi;


import bot.update.ContentUpdater;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.repository.PlanAccomplishedRepository;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.*;
import objects.shared.ContentByPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatfuelContentSender {

    @Autowired
    private ContentUpdater contentUpdater;

    @Autowired
    private ChatfuelBroadcaster broadcaster;

    @Autowired
    private Environment env;

    @Autowired
    private PlanAccomplishedRepository planAccomplishedRepository;

    @Autowired
    private AsyncBroadcast asyncBroadcast;

    private ObjectMapper objectMapper = new ObjectMapper();


    private ContentByPlatform getContentByPlatform(String json) throws IOException {
        ContentByPlatform contentByPlatform = objectMapper.readValue(json, ContentByPlatform.class);

        if (contentByPlatform.getChatfuelResponse() == null) {
            throw new RuntimeException("contentByPlatform has no chatfuel response in it");
        } else {
            return contentByPlatform;
        }
    }


    public void broadcastToMakeChatfuelRequestContent(Platform platform, Plan plan, User user, PlatformToUser platformToUser) {
        broadcaster.broadcastBlockToUser(platformToUser.getPlatformSpecificData(), env.getProperty("chatfuel_update_block"), null);
    }




    //todo add logging
    // is followup is for that(only plan based questions get logged into plan accomplished)
    private ChatfuelResponse getContentAndScheduleFollowup(User user, Long questionId, boolean isFollowup, String chatfuelUserId) {
        Question question = contentUpdater.getQuestionsById().get(questionId);
        ContentByPlatform contentByPlatform = null;
        try {
            contentByPlatform = getContentByPlatform(question.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        asyncBroadcast.cake();

        if (question.getLeadsToQuestionId() != null) {
            asyncBroadcast.broadcastNextQuestion(chatfuelUserId, String.valueOf(question.getLeadsToQuestionId()));
        }
        System.out.println("past aSync");
        return contentByPlatform.getChatfuelResponse();
    }


    public ChatfuelResponse getChatfuelContent(Platform platform, Plan plan, User user, PlatformToUser platformToUser) {

        //todo since spring requests are async, you might Start getting next request before this is actually saved,
        // causing the same question to be sent multiple times
        // to fix it, we will need to lock requests for users that are being processed or somehow limit the endpoint.. stuff to think about
        // might also need to move this to some place we get actual confirmation
        PlanAccomplished planAccomplished = new PlanAccomplished();
        planAccomplished.setPlanId(plan.getId());
        planAccomplished.setUserId(user.getId());
        planAccomplishedRepository.save(planAccomplished);


        return getContentAndScheduleFollowup(user, plan.getQuestionId(), false, platformToUser.getPlatformSpecificData());
    }


    public ChatfuelResponse getChatfuelContentByQuestionId(User user, Long questionId, String chatfuelUserId) {
        return getContentAndScheduleFollowup(user, questionId, true, chatfuelUserId);
    }
}
