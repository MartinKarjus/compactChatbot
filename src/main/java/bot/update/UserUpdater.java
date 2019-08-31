package bot.update;

import db.dao.PlanDao;
import db.dao.PlatformDao;
import db.dao.QuestionGroupDao;
import db.dao.UserDao;
import objects.dbentities.Platform;
import objects.dbentities.PlatformToUser;
import objects.dbentities.QuestionGroup;
import objects.dbentities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserUpdater {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionGroupDao questionGroupDao;

    @Autowired
    private PlatformDao platformDao;

    private Map<Long, User> usersById = new HashMap<>();
    private Map<Long, QuestionGroup> groupsById = new HashMap<>();
    private Map<Long, List<Long>> planAccomplishedUserToPlansByIds = new HashMap<>();
    private Map<Long, Platform> platformById = new HashMap<>();
    private Map<Long, List<PlatformToUser>> userToPlatformToUser = new HashMap<>();



    public void update() {
        usersById = userDao.getAllUsersById();
        groupsById = questionGroupDao.getAllQuestionGroupsById();
        planAccomplishedUserToPlansByIds = planDao.getPlanAccomplishedToUserById();
        platformById = platformDao.getAllPlatformsById();
        userToPlatformToUser = platformDao.getUserIdtoPlatformToUser();
    }

    public PlatformToUser getPlatformToUserById(Long userId, String platformName) {
        for (PlatformToUser platformToUser : userToPlatformToUser.get(userId)) {
            Platform p = platformById.get(platformToUser.getPlatformId());
            if (p.getName().equals(platformName)) {
                return platformToUser;
            }
        }

        throw new IllegalArgumentException("Platform with name:'" + platformName + "' has no corresponding user with id:" + userId);
    }

    public Platform getPlatformByName(String platformName) {
        for (Map.Entry<Long, Platform> entry : platformById.entrySet()) {
            if(entry.getValue().getName().equals(platformName)) {
                return entry.getValue();
            }
        }

        throw new IllegalArgumentException("No platform named: " + platformName);
    }


    public Map<Long, User> getUsersById() {
        return usersById;
    }

    public Map<Long, QuestionGroup> getGroupsById() {
        return groupsById;
    }


    public Map<Long, List<Long>> getPlanAccomplishedUserToPlansByIds() {
        return planAccomplishedUserToPlansByIds;
    }

    public Map<Long, Platform> getPlatformById() {
        return platformById;
    }

    public Map<Long, List<PlatformToUser>> getUserToPlatformToUser() {
        return userToPlatformToUser;
    }
}
