package bot.user;

import bot.temp.ChatfuelFileContentReader;
import db.repository.BotUserRepository;
import objects.chatfuel.ChatfuelRegistrationRequest;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.BotUser;
import objects.dbentities.PlatformToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserRegistration {

    @Autowired
    private BotUserRepository botUserRepository;

    @Autowired
    private CompanyRegistration companyRegistration;

    @Autowired
    private GroupRegistration groupRegistration;

    @Autowired
    private UserToPlatformRegistration userToPlatformRegistration;

    public ChatfuelResponse registerUserChatfuel(ChatfuelRegistrationRequest req) {

        BotUser user = new BotUser();
        user.setFirstName(req.getFirstName());
        user.setLastname(req.getLastName());
        companyRegistration.regTestUser(user);
        groupRegistration.regUserToGroup(user, user.getCompanyId());
        user.setTeamId(req.getTeamId());
        user.setScore(0L);
        user = botUserRepository.save(user);

        userToPlatformRegistration.saveUserToPlatform(user, "chatfuel", req.getChatfuelUserId());

        ChatfuelResponse res = null; // todo rmv
        try {
            res = new ChatfuelFileContentReader().getResponse("/json/contentForTesting/content1.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("response is:");
        System.out.println(res);
        return res;
    }
}
