package controller;

import bot.chatfuelapi.ChatfuelContentUpdater;
import dao.BotUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatfuelController {

    @Autowired
    private ChatfuelContentUpdater contentUpdater;

    @Autowired
    private BotUserDao botUserDao;

    @GetMapping("chatfuel/update")
    public String chatfuelUpdate() {
        contentUpdater.updateUsers();

        System.out.println("checking users, sending new msges if needed");
        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"Users updated\"},\n" +
                " ]\n" +
                "}";
    }



}
