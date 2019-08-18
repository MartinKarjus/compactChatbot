package controller;


import bot.ContentSender;
import bot.chatfuelapi.ChatfuelBroadcaster;
import bot.chatfuelapi.ChatfuelContentSender;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.dao.UserDao;
import db.repository.UserRepository;
import objects.chatfuel.ChatfuelRequest;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatfuelController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatfuelBroadcaster chatfuelBroadcaster;

    @Autowired
    private ContentSender contentSender;

    @Autowired
    private ChatfuelContentSender chatfuelContentSender;


    @PostMapping("/chatfuel/getcontent")
    public String chatfuelGetContent(@RequestBody ChatfuelRequest request) throws JsonProcessingException {
        if(request.getChatfuelUserId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        ChatfuelResponse res = null;

        if(request.getQuestionId() == null) {
            res = (ChatfuelResponse) contentSender.chatfuelContentRequest(request.getChatfuelUserId(), "chatfuel");
            System.out.println("SENDING1:" + res);
        } else {
            res = contentSender.getContentById(request.getChatfuelUserId(), request.getQuestionId());
            System.out.println("SENDING2:" + res);

        }
        ObjectMapper mapper = new ObjectMapper();

        String str = mapper.writeValueAsString(res);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String str2 = mapper.writeValueAsString(res);
        System.out.println(str);
        System.out.println(str2);

        return str;
//        return "{\n" +
//                " \"messages\": [\n" +
//                "   {\"text\": \"User id is\"},\n" +
//                "   {\"text\": \"" + request.getChatfuelUserId()+"\"}\n" +
//                " ]\n" +
//                "}";
    }





}
