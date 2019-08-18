package bot;


import bot.chatfuelapi.ChatfuelContentSender;
import bot.chatfuelapi.ChatfuelUserManager;
import bot.update.ContentUpdater;
import bot.update.UserUpdater;
import objects.chatfuel.ChatfuelResponse;
import objects.dbentities.Plan;
import objects.dbentities.Platform;
import objects.dbentities.PlatformToUser;
import objects.dbentities.User;
import objects.shared.ContentRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentSender {

    @Autowired
    private ContentUpdater contentUpdater;

    @Autowired
    private UserUpdater userUpdater;

    @Autowired
    private ContentManager contentManager;

    @Autowired
    private ChatfuelContentSender chatfuelContentSender;

    @Autowired
    private ChatfuelUserManager chatfuelUserManager;

    @Autowired
    private BotLogger botLogger;


    //todo parts of the update need to be made after X time
    //      for example we dont need to update users every time there's a new request, every x minutes would be fine
    // also we need to automap with annotations instead of doing it manually(after we get our own db set up, as it will be diffrent for postgresql
    // we could use caching for everything in content and userupdater
    private void refreshContent(boolean forceUpdate) {
        contentUpdater.setQuestionGroupIdToCurrentDayPlans(new HashMap<>());

        if (forceUpdate || true) { // compare times between now and last update here(left or true here while not checking)
            //todo when updating users and question groups, need to add check if Active is true
            contentUpdater.update();
            userUpdater.update();
        }
    }




    private void sendContent(User user, Plan plan) {
        List<Platform> platformsForUser = contentManager.getPlatformsForUser(user);
        for (Platform platform : platformsForUser) {
            if (platform.getName().equals("chatfuel")) {
                PlatformToUser platformToUser = userUpdater.getPlatformToUserById(user.getId(), platform.getName());
                chatfuelContentSender.broadcastToMakeChatfuelRequestContent(platform, plan, user, platformToUser);
            } else {
                throw new IllegalArgumentException("Invalid platform name: " + platform.getName());
            }
        }
    }

    private ContentRequestResponse getContent(User user, Plan plan, Platform platform) {
        if (platform.getName().equals("chatfuel")) {
            PlatformToUser platformToUser = userUpdater.getPlatformToUserById(user.getId(), platform.getName());
            return chatfuelContentSender.getChatfuelContent(platform, plan, user, platformToUser);
        } else {
            throw new IllegalArgumentException("Invalid platform name: " + platform.getName());
        }
    }

    private void serviceUsers() {
        Map<User, List<Plan>> contentToSend = contentManager.getUnsentContent();


        for (Map.Entry<User, List<Plan>> entry : contentToSend.entrySet()) {
            if (entry.getValue().size() > 1) {
                sendContent(entry.getKey(), contentManager.getLatestToSend(entry.getValue()));
            } else if (entry.getValue().size() == 1) {
                sendContent(entry.getKey(), entry.getValue().get(0));
            } else {
                System.out.println("No content to send to user:\n"
                        + "id: " + entry.getKey().getId() + "\n"
                        + "name: " + entry.getKey().getFirstName() + " " + entry.getKey().getLastname());
            }
        }
    }

    private ContentRequestResponse serviceUser(User user, String platformName) {
        List<Plan> plans = contentManager.getUnsentContent().get(user);
        Platform platform = userUpdater.getPlatformByName("chatfuel");//todo change to apply to all platforms

        if (plans.size() > 1) {
            return getContent(user, contentManager.getLatestToSend(plans), platform);
        } else if (plans.size() == 1) {
            return getContent(user, plans.get(0), platform);
        } else {
            System.out.println("No content to send to user:\n"
                    + "id: " + user.getId() + "\n"
                    + "name: " + user.getFirstName() + " " + user.getLastname());
        }

        return null;
    }

    //cant send content to chatfuel, need to request it instead
    // so chatfuel users get broadcasted a block here to request content individually instead of actually sending it
    public void sendOutContent() {
        refreshContent(false);


        serviceUsers();
    }

    public ContentRequestResponse chatfuelContentRequest(String userId, String platformName) {
        refreshContent(false);

        if(platformName.equals("chatfuel")) {
            return serviceUser(chatfuelUserManager.getUserWithChatfuelUserId(userId), platformName);
        } else {
            return null;
        }

    }

    public ChatfuelResponse getContentById(String chatfuelUserId, String questionId) {
        refreshContent(false);

        return chatfuelContentSender.getChatfuelContentByQuestionId(chatfuelUserManager.getUserWithChatfuelUserId(chatfuelUserId), questionId, chatfuelUserId);
    }


}
