package controller;


import bot.ContentSender;
import bot.chatfuelapi.ChatfuelBroadcaster;
import bot.chatfuelapi.ChatfuelContentSender;
import bot.temp.ChatfuelFileContentReader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.dao.UserDao;
import db.repository.PlatformToUserRepository;
import db.repository.QuestionRepository;
import db.repository.UserRepository;
import objects.chatfuel.ChatfuelRegistrationRequest;
import objects.chatfuel.ChatfuelRequest;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.PlatformToUser;
import objects.dbentities.Question;
import objects.dbentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import util.ContentGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


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

    @Autowired
    private ContentGenerator contentGenerator;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PlatformToUserRepository platformToUserRepository;


    private boolean init = false;


    @GetMapping("initialize")
    public void init() {
        contentGenerator.addStaticValues();

        //todo put in a function thats called after application initialization is done
        ChatfuelFileContentReader chatfuelFileContentReader = new ChatfuelFileContentReader();
        Question questionIntro = new Question();
        questionIntro.setName("intro");
        questionIntro.setDescription("for testing");
        questionIntro.setText(chatfuelFileContentReader.getContentAsJson("/json/contentForTesting/content1.json"));
        questionIntro.setMediaId(1L);
        questionRepository.save(questionIntro);
        init = true;

    }

    @PostMapping("/chatfuel/answer")
    public String chatFuelAnswerUpdate(@RequestBody ChatfuelRequest request) {
        System.out.println("Getting request");
        System.out.println(request.getMessenger_user_id());
        System.out.println(request);
        answersDao.save(Long.parseLong(request.getLast_visited_block_id()),
                new Timestamp(System.currentTimeMillis()),
                Long.parseLong(request.getMessenger_user_id()),
                Long.parseLong("0"),
                request.getLast_clicked_button_name());


    @GetMapping("populate")
    public String populate() {
        if (!init) {
            init();
        }
        contentGenerator.addContent();

        System.out.println(userRepository.findAll());
        //userDao.getAllUsers().forEach(u -> System.out.println(u.getFirstName() + " " + u.getLastname()));
        return "Success";
    }

    @PostMapping("chatfuel/getInitial") // also initializes user
    public ChatfuelResponse getInitial(@RequestBody ChatfuelRegistrationRequest req) throws IOException {
        if (!init) {
            init();
        }
        System.out.println("user id is: " + req.getChatfuelUserId());
        System.out.println("user first and lastnames are: " + req.getFirstName() + " " + req.getLastName());
        // my id: 2449441191741397
        // marie id: 2518988008117216
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastname(req.getLastName());
        user.setQuestionGroupId(1L);
        user.setTeamId(1L);
        user.setCompanyId(1L);
        user.setScore(0L);
        User u = userRepository.save(user);

        //todo select platform 'chatfuel' by name, then get id, then tie the user to the id
        PlatformToUser platformToUser = new PlatformToUser();
        platformToUser.setPlatformId(1L);
        platformToUser.setPlatformSpecificData(req.getChatfuelUserId());
        platformToUser.setUserId(u.getId());
        platformToUserRepository.save(platformToUser);

        ChatfuelResponse res = new ChatfuelFileContentReader().getResponse("/json/contentForTesting/content1.json");
        System.out.println("response is:");
        System.out.println(res);
        return res;
    }




    @PostMapping("/chatfuel/getcontent")
    public ChatfuelResponse chatfuelGetContent(@RequestBody ChatfuelRequest request) throws JsonProcessingException {


        if(request.getChatfuelUserId() == null) {
            throw new IllegalArgumentException("User id cannot be null when getting content for chatfuel");
        }

        ChatfuelResponse res = null;

        if(request.getQuestionId() == null) {
            System.out.println("--1--");
            res = (ChatfuelResponse) contentSender.chatfuelContentRequest(request.getChatfuelUserId(), "chatfuel");
            System.out.println("SENDING1:" + res);
        } else {
            System.out.println("--2--");
            res = contentSender.getContentById(request.getChatfuelUserId(), request.getQuestionId());
            System.out.println("SENDING2:" + res);

        }
        ObjectMapper mapper = new ObjectMapper();

        String str = mapper.writeValueAsString(res);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String str2 = mapper.writeValueAsString(res);
        System.out.println(str);
        System.out.println(str2);

        System.out.println("get content request:" + mapper.writeValueAsString(request));

        return res;
    }

    @GetMapping("/chatfuel/getContent")
    public void getContent() {
        System.out.println("\ngetting content");

        contentSender.sendOutContent();
    }





}
