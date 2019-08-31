package bot.chatfuelapi;


import bot.update.ContentUpdater;
import bot.update.UserUpdater;
import db.dao.UserDao;
import objects.dbentities.Plan;
import objects.dbentities.PlatformToUser;
import objects.dbentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ChatfuelUserManager {

    @Autowired
    private UserUpdater userUpdater;

    public User getUserWithChatfuelUserId(String chatfuelId) {
        //easier to do with a db request i guess but i didnt have any data to test on when i made this
        System.out.println("chatfuel user id is: " + chatfuelId);
        System.out.println("user to platform map: " +userUpdater.getUserToPlatformToUser());
        for (Map.Entry<Long, List<PlatformToUser>> entry : userUpdater.getUserToPlatformToUser().entrySet()) {
            for (PlatformToUser platformToUser : entry.getValue()) {
                System.out.println("specific data: " + platformToUser.getPlatformSpecificData());
                if (platformToUser.getPlatformSpecificData().equals(chatfuelId)
                        && userUpdater.getPlatformById().get(platformToUser.getPlatformId()).getName().equals("chatfuel")) {
                    return userUpdater.getUsersById().get(platformToUser.getUserId());
                }
            }
        }

        throw new IllegalArgumentException("No such chatfuel id in database: " + chatfuelId);
    }

}
