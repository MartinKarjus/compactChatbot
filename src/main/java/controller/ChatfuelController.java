package controller;

import chatfuelapi.ChatfuelUserHandler;
import objects.chatfuel.ChatfuelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ChatfuelController {

    final
    ChatfuelUserHandler userHandler;

    @Autowired
    public ChatfuelController(ChatfuelUserHandler userHandler) {
        this.userHandler = userHandler;
    }

    @GetMapping("chatfuel/update")
    public String chatfuelUpdate() {
        System.out.println("checking users, sending new msges if needed");
        userHandler.updateUsers();
        return "{\n" +
                " \"messages\": [\n" +
                "   {\"text\": \"Users updated\"},\n" +
                " ]\n" +
                "}";
    }
}
