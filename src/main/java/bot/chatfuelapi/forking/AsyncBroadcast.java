package bot.chatfuelapi.forking;


import bot.chatfuelapi.ChatfuelBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AsyncBroadcast {

    @Autowired
    private Environment env;

    @Autowired
    private ChatfuelBroadcaster broadcaster;


    @Async
    void broadcastNextQuestion(String chatfuelUserId, String questionId, int sleepTimeInMilliSec) {
        System.out.println("planning broadcast for<chatfuelUserId, questionId>: " + chatfuelUserId + ", " + questionId);
        Map<String, String> attributes = new HashMap<>();
        attributes.put(env.getProperty("chatfuel_question_id_attribute_name"), questionId);
        try {
            Thread.sleep(sleepTimeInMilliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        broadcaster.broadcastBlockToUser(chatfuelUserId, env.getProperty("chatfuel_specific_content_block"), attributes);
    }
}


