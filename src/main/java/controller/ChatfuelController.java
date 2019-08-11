package controller;

import bot.chatfuelapi.ChatfuelContentUpdater;
import dao.AnswersDao;
import dao.UserDao;
import objects.chatfuel.ChatfuelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatfuelController {

    @Autowired
    private ChatfuelContentUpdater contentUpdater;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswersDao answersDao;

    @PostMapping("/chatfuel/answer")
    public String chatFuelAnswerUpdate(@RequestBody ChatfuelRequest request) {
        System.out.println("Getting request");
        System.out.println(request.getId());
        System.out.println(request);

        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"Users updated\"},\n" +
                " ]\n" +
                "}";
    }

}
