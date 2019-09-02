package bot.chatfuelapi;


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
    void broadcastNextQuestion(String chatfuelUserId, String questionId) {
        System.out.println("Async broadcast...");
        Map<String, String> attributes = new HashMap<>();
        attributes.put(env.getProperty("chatfuel_question_id_attribute_name"), questionId);
        try {
            Thread.sleep(15000); // todo change to duration based on text length last sent
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sending to broadcast: "
                + " | " + chatfuelUserId
                + " | " + env.getProperty("chatfuel_specific_content_block")
                + " | " + attributes);
        broadcaster.broadcastBlockToUser(chatfuelUserId, env.getProperty("chatfuel_specific_content_block"), attributes);
    }
}


