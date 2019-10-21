package bot.user;

import db.repository.PlatformRepository;
import db.repository.PlatformToUserRepository;
import objects.dbentities.BotUser;
import objects.dbentities.Platform;
import objects.dbentities.PlatformToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserToPlatformRegistration {

    @Autowired
    private PlatformToUserRepository platformToUserRepository;

    @Autowired
    private PlatformRepository platformRepository;




    public void saveUserToPlatform(BotUser user, String platformName, String platformSpecificId) {
        Platform p = platformRepository.findByName(platformName);
        if(p == null) {
            p = new Platform();
            p.setName(platformName);
            platformRepository.save(p);
        }

        PlatformToUser platformToUser = new PlatformToUser();
        platformToUser.setPlatformId(p.getId());
        platformToUser.setPlatformSpecificData(platformSpecificId);
        platformToUser.setUserId(user.getId());
        platformToUserRepository.save(platformToUser);

    }
}
