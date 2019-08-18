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
        System.out.println("id:" + chatfuelId);
        System.out.println("map:" + userUpdater.getUserToPlatformToUser());
        for (Map.Entry<Long, List<PlatformToUser>> entry : userUpdater.getUserToPlatformToUser().entrySet()) {
            for (PlatformToUser platformToUser : entry.getValue()) {
                if (platformToUser.getPlatformSpecificData().equals(chatfuelId)
                        && userUpdater.getPlatformById().get(platformToUser.getId()).getName().equals("chatfuel")) {
                    return userUpdater.getUsersById().get(platformToUser.getUserId());
                }
            }
        }

        throw new IllegalArgumentException("No such chatfuel id in database: " + chatfuelId);
    }

}
