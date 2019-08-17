package controller;

import bot.ContentSender;
import dao.UserDao;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class ChatfuelTestController {

    @Autowired
    private UserDao userDao;

//    @Autowired
//    private ContentSender contentSender;

    @GetMapping("test")
    public String tryGet() {
        System.out.println("test Get");
        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"This thing\"},\n" +
                "   {\"text\": \"Actually works\"}\n" +
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
