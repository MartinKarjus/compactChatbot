package controller;



import bot.ContentSender;
import bot.chatfuelapi.ChatfuelBroadcaster;
import bot.chatfuelapi.ChatfuelContentSender;
import bot.temp.ChatfuelFileContentReader;
import db.dao.UserDao;
import db.repository.PlatformToUserRepository;
import db.repository.QuestionRepository;
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
import db.repository.UserRepository;
import util.ContentGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ChatfuelTestController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PlatformToUserRepository platformToUserRepository;

    @Autowired
    private ChatfuelBroadcaster chatfuelBroadcaster;

    @Autowired
    private ContentSender contentSender;

    @Autowired
    private ChatfuelContentSender chatfuelContentSender;

    @Autowired
    private ContentGenerator contentGenerator;


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

    @PostMapping("chatfuel/getInitial")
    public ChatfuelResponse getInitial(@RequestBody ChatfuelRegistrationRequest req) throws IOException {
        if(!init) {
            init();
        }
        System.out.println("user id is:");
        System.out.println(req.getChatfuelUserId());
        // my id: 2449441191741397
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastname(req.getLastName());
        user.setQuestionGroupId(1L);
        user.setTeamId(1L);
        user.setCompanyId(1L);
        user.setScore(0L);
        User u = userRepository.save(user);

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


    @GetMapping("populate")
    public String populate() {
        if(!init) {
            init();
        }
        contentGenerator.addContent();

        System.out.println(userRepository.findAll());
        //userDao.getAllUsers().forEach(u -> System.out.println(u.getFirstName() + " " + u.getLastname()));
        return "Success";
    }

    @GetMapping("test")
    public String tryGet() {
        System.out.println("test Get");


        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"This thing\"},\n" +
                "   {\"text\": \"works\"}\n" +
                " ]\n" +
                "}";
    }

    @PostMapping("test")
    public String tryPost() {
        System.out.println("test Post");


        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"This thing\"},\n" +
                "   {\"text\": \"works\"}\n" +
                " ]\n" +
                "}";
    }

    @GetMapping("testBroadcast")
    public void makeBroadcast() {
        System.out.println("making broadcast...");
        chatfuelBroadcaster.broadcastBlockToUser("2449441191741397", "contentrequester", null);
    }

    @PostMapping("chatfuel/id")
    public String chatfuelGetId(@RequestBody ChatfuelRequest request) {
        System.out.println("user id is:");
        System.out.println(request.getChatfuelUserId());
        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"User id is\"},\n" +
                "   {\"text\": \"" + request.getChatfuelUserId()+"\"}\n" +
                " ]\n" +
                "}";
    }


    @PostMapping("/chatfuel/answer")
    public String chatFuelAnswerUpdate(@RequestBody ChatfuelRequest request) {
        System.out.println("Getting request");
        System.out.println(request.getChatfuelUserId());
        System.out.println(request);
        User user = new User();
        user.setFirstName("firstName");
        user.setLastname("lastname");
        user.setQuestionGroupId(1L);


        userRepository.save(user);
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"User registered, current users: " + users + "\"},\n" +
                " ]\n" +
                "}";
    }

    @GetMapping("sendContent")
    public ChatfuelResponse cake() {
        ChatfuelResponse response = null;
        //response = contentSender.getForChatfuel();
        //contentSender.update();

        return response;
    }

//    @GetMapping("cake2")
//    public List<User> cake2() {
//        System.out.println("cakeRequest2");
//        return userDao.getAllUsersFromPlatform("chatfuel");
//    }
//    @GetMapping("cake3")
//    public List<Usergroup> cake3() {
//        System.out.println("cakeRequest3");
//        List<Usergroup> usergroups = userDao.getUsergroupsByUsers(userDao.getAllUsersFromPlatform("chatfuel"));
//        usergroups.forEach(g -> System.out.println(g.getStartDate()));
//        return usergroups;
//    }

//    @PostMapping("test")
//    public String tryPost(@RequestBody ChatfuelRequest req) {
//        System.out.println("getting req");
//        System.out.println(req.getId());
//        System.out.println(req);
//        //String jsonString = req.getParameter("json");
//
//        return "{\n" +
//                "  \"messages\": [\n" +
//                "    {\n" +
//                "      \"attachment\": {\n" +
//                "        \"type\": \"video\",\n" +
//                "        \"payload\": {\n" +
//                "          \"url\": \"https://rockets.chatfuel.com/assets/video.mp4\"\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//    }


    @PostMapping("test/list")
    public String tryList(@RequestBody String req) {
        System.out.println(req);
        System.out.println("getting req");
        return "{\n" +
                " \"messages\": [\n" +
                "    {\n" +
                "      \"attachment\":{\n" +
                "        \"type\":\"template\",\n" +
                "        \"payload\":{\n" +
                "          \"template_type\":\"list\",\n" +
                "          \"top_element_style\":\"large\",\n" +
                "          \"elements\":[\n" +
                "            {\n" +
                "              \"title\":\"Chatfuel Rockets Jersey\",\n" +
                "              \"image_url\":\"http://rockets.chatfuel.com/assets/shirt.jpg\",\n" +
                "              \"subtitle\":\"Size: M\",\n" +
                "              \"buttons\":[\n" +
                "                {\n" +
                "                  \"type\":\"web_url\",\n" +
                "                  \"url\":\"https://rockets.chatfuel.com/store\",\n" +
                "                  \"title\":\"View Item\"\n" +
                "                }\n" +
                "              ]\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\":\"Chatfuel Rockets Jersey\",\n" +
                "              \"image_url\":\"http://rockets.chatfuel.com/assets/shirt.jpg\",\n" +
                "              \"subtitle\":\"Size: L\",\n" +
                "              \"default_action\": {\n" +
                "                \"type\": \"web_url\",\n" +
                "                \"url\": \"https://rockets.chatfuel.com/store\",\n" +
                "                \"messenger_extensions\": true\n" +
                "              },\n" +
                "              \"buttons\":[\n" +
                "                {\n" +
                "                  \"type\":\"web_url\",\n" +
                "                  \"url\":\"https://rockets.chatfuel.com/store\",\n" +
                "                  \"title\":\"View Item\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @PostMapping("test/choice")
    public String tryChoice(@RequestBody String req) {
        System.out.println(req);
        System.out.println("getting req");
        return "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"attachment\": {\n" +
                "        \"type\": \"template\",\n" +
                "        \"payload\": {\n" +
                "          \"template_type\": \"button\",\n" +
                "          \"text\": \"Hello!\",\n" +
                "          \"buttons\": [\n" +
                "            {\n" +
                "              \"type\": \"show_block\",\n" +
                "              \"block_names\": [\"name of block\"],\n" +
                "              \"title\": \"Show Block\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"web_url\",\n" +
                "              \"url\": \"https://rockets.chatfuel.com\",\n" +
                "              \"title\": \"Visit Website\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"url\": \"http://89.235.216.116:8080/api/test\",\n" +
                "              \"type\":\"json_plugin_url\",\n" +
                "              \"title\":\"Postback\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }


    //@PostMapping("chatfuel/getupdate")


}
