package controller;

import bot.user.UserRegistration;
import objects.chatfuel.ChatfuelRegistrationRequest;
import objects.chatfuel.ChatfuelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private UserRegistration userRegistration;

    @PostMapping("/user/testuser/chatfuel") // also initializes user
    public ChatfuelResponse getInitial(@RequestBody ChatfuelRegistrationRequest req) throws IOException {
        System.out.println("user id is: " + req.getChatfuelUserId());
        System.out.println("user first and lastnames are: " + req.getFirstName() + " " + req.getLastName());
        // my id: 2449441191741397
        // marie id: 2518988008117216

        ChatfuelResponse res = userRegistration.registerUserChatfuel(req);

        return res;
    }

}
