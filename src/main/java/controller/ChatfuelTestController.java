package controller;


import bot.chatfuelapi.ChatfuelBroadcaster;
import db.repository.BotUserRepository;
import objects.chatfuel.ChatfuelRegistrationRequest;
import objects.chatfuel.ChatfuelRequest;
import objects.chatfuel.ChatfuelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import util.FileUtil;
import util.contentreader.ContentFileReader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class ChatfuelTestController {


    @Autowired
    private BotUserRepository userRepository;


    @Autowired
    private ChatfuelBroadcaster chatfuelBroadcaster;



    @GetMapping("getcake")
    public WebAsyncTask<Map<String, Object>> simpleAsyncTask(@RequestParam(defaultValue="5") long t) {
        return new WebAsyncTask<Map<String, Object>>(10000, () -> {

            System.out.println("starting wait.." + LocalDateTime.now());
            Thread.sleep(t * 1000);
            System.out.println("ending wait..." + LocalDateTime.now());

            return Collections.<String, Object>singletonMap("key", "success");
        });
    }


    @GetMapping("test/{a}")
    public String tryGet(@PathVariable(value="a") String id) {
        System.out.println("test Get");
        System.out.println("id:" + id);

        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"This thing\"},\n" +
                "   {\"text\": \"works\"}\n" +
                " ]\n" +
                "}";
    }


    @PostMapping("test/{a}")
    public String cake(@RequestBody String req, @PathVariable(value="a") String id) {
        System.out.println("test Post");
        System.out.println("id:" + id);
        System.out.println("req is: " + req);

        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"This thing\"},\n" +
                "   {\"text\": \"works\"}\n" +
                " ]\n" +
                "}";
    }

    @GetMapping("emoji")
    public ResponseEntity<String> getEmoji() throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type",
                "text/html; charset=utf-8");
        List<List<String>> l = new ContentFileReader().readFile("/botcontent/Chatbot flow new - Sheet1.csv");
        String s = "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"emoji: \uD83D\uDE02\uD83D\uDE0D\uD83C\uDF89\uD83D\uDC4D\"},\n" +
                "   {\"text\": \"a whole file: " + l.toString() + "\"}\n" +
                " ]\n" +
                "}";
        return ResponseEntity.ok().headers(responseHeaders).body(s);
    }


    @PostMapping("test/{userId}/{questionId}/{questionAnswerId}")
    public String tryPost(@RequestBody ChatfuelRegistrationRequest res,
                          @PathVariable(value= "userId") String userId,
                          @PathVariable(value = "questionId") String questionId,
                          @PathVariable(value = "questionAnswerId") String questionAnswerId) {
        System.out.println("");
        System.out.println("test Post");
        System.out.println("id:" + userId);


        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"Name: " + res.getFirstName() + "\"},\n" +
                "   {\"text\": \"Id: " + res.getChatfuelUserId() + "\"}\n" +
                " ]\n" +
                "}";
    }

    @GetMapping("testBroadcast")
    public void makeBroadcast() {
        System.out.println("\nmaking broadcast...");
        chatfuelBroadcaster.broadcastBlockToUser("2449441191741397", "contentrequester", null);
    }

    @PostMapping("chatfuel/id")
    public String chatfuelGetId(@RequestBody ChatfuelRequest request) {
        System.out.println("user id is:");
        System.out.println(request.getChatfuelUserId());
        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"BotUser id is\"},\n" +
                "   {\"text\": \"" + request.getChatfuelUserId() + "\"}\n" +
                " ]\n" +
                "}";
    }

    @GetMapping("testing")
    public String testImg() {
        return FileUtil.readFileFromClasspath("json/randomtesting.json");
    }


//    @PostMapping("/chatfuel/answer")
//    public String chatFuelAnswerUpdate(@RequestBody ChatfuelRequest request) {
//        System.out.println("Getting request");
//        System.out.println(request.getChatfuelUserId());
//        System.out.println(request);
//        BotUser user = new BotUser();
//        user.setFirstName("firstName");
//        user.setLastname("lastname");
//        user.setQuestionGroupId(1L);
//
//
//        userRepository.save(user);
//        List<BotUser> users = new ArrayList<>();
//        userRepository.findAll().forEach(users::add);
//
//        return "{\n" +
//                " \"messages\": [\n" +
//                "   {\"text\": \"BotUser registered, current users: " + users + "\"},\n" +
//                " ]\n" +
//                "}";
//    }

    @GetMapping("sendContent")
    public ChatfuelResponse cake() {
        ChatfuelResponse response = null;
        //response = contentSender.getForChatfuel();
        //contentSender.update();

        return response;
    }

//    @GetMapping("cake2")
//    public List<BotUser> cake2() {
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


//    @PostMapping("test/list")
//    public String tryList(@RequestBody String req) {
//        System.out.println(req);
//        System.out.println("getting req");
//        return "{\n" +
//                " \"messages\": [\n" +
//                "    {\n" +
//                "      \"attachment\":{\n" +
//                "        \"type\":\"template\",\n" +
//                "        \"payload\":{\n" +
//                "          \"template_type\":\"list\",\n" +
//                "          \"top_element_style\":\"large\",\n" +
//                "          \"elements\":[\n" +
//                "            {\n" +
//                "              \"title\":\"Chatfuel Rockets Jersey\",\n" +
//                "              \"image_url\":\"http://rockets.chatfuel.com/assets/shirt.jpg\",\n" +
//                "              \"subtitle\":\"Size: M\",\n" +
//                "              \"buttons\":[\n" +
//                "                {\n" +
//                "                  \"type\":\"web_url\",\n" +
//                "                  \"url\":\"https://rockets.chatfuel.com/store\",\n" +
//                "                  \"title\":\"View Item\"\n" +
//                "                }\n" +
//                "              ]\n" +
//                "            },\n" +
//                "            {\n" +
//                "              \"title\":\"Chatfuel Rockets Jersey\",\n" +
//                "              \"image_url\":\"http://rockets.chatfuel.com/assets/shirt.jpg\",\n" +
//                "              \"subtitle\":\"Size: L\",\n" +
//                "              \"default_action\": {\n" +
//                "                \"type\": \"web_url\",\n" +
//                "                \"url\": \"https://rockets.chatfuel.com/store\",\n" +
//                "                \"messenger_extensions\": true\n" +
//                "              },\n" +
//                "              \"buttons\":[\n" +
//                "                {\n" +
//                "                  \"type\":\"web_url\",\n" +
//                "                  \"url\":\"https://rockets.chatfuel.com/store\",\n" +
//                "                  \"title\":\"View Item\"\n" +
//                "                }\n" +
//                "              ]\n" +
//                "            }\n" +
//                "          ]\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//    }
//
//    @PostMapping("test/choice")
//    public String tryChoice(@RequestBody String req) {
//        System.out.println(req);
//        System.out.println("getting req");
//        return "{\n" +
//                "  \"messages\": [\n" +
//                "    {\n" +
//                "      \"attachment\": {\n" +
//                "        \"type\": \"template\",\n" +
//                "        \"payload\": {\n" +
//                "          \"template_type\": \"button\",\n" +
//                "          \"text\": \"Hello!\",\n" +
//                "          \"buttons\": [\n" +
//                "            {\n" +
//                "              \"type\": \"show_block\",\n" +
//                "              \"block_names\": [\"name of block\"],\n" +
//                "              \"title\": \"Show Block\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "              \"type\": \"web_url\",\n" +
//                "              \"url\": \"https://rockets.chatfuel.com\",\n" +
//                "              \"title\": \"Visit Website\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "              \"url\": \"http://89.235.216.116:8080/api/test\",\n" +
//                "              \"type\":\"json_plugin_url\",\n" +
//                "              \"title\":\"Postback\"\n" +
//                "            }\n" +
//                "          ]\n" +
//                "        }\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//    }


    //@PostMapping("chatfuel/getupdate")


}
