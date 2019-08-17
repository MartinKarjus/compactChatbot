package controller;

import bot.chatfuelapi.ChatfuelContentUpdater;
import dao.AnswersDao;
import dao.UserDao;
import objects.chatfuel.ChatfuelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


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
        System.out.println(request.getMessenger_user_id());
        System.out.println(request);
        answersDao.save(Long.parseLong(request.getLast_visited_block_id()),
                new Timestamp(System.currentTimeMillis()),
                Long.parseLong(request.getMessenger_user_id()),
                Long.parseLong("0"),
                request.getLast_clicked_button_name());

        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"Users updated\"},\n" +
                " ]\n" +
                "}";
    }

}
