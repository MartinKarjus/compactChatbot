package db.dao;

import objects.dbentities.Platform;
import objects.dbentities.PlatformToUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlatformDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<Platform> getAllPlatforms() {
        return
                entityManager.createQuery(
                        "select u from Platform u", Platform.class)
                        .getResultList();
    }

    public List<PlatformToUser> platformToUsers() {
        return
                entityManager.createQuery(
                        "select u from PlatformToUser u", PlatformToUser.class)
                        .getResultList();
    }


    public Map<Long, Platform> getAllPlatformsById() {
        HashMap<Long, Platform> idToPlatform = new HashMap<>();
        for (Platform platform : getAllPlatforms()) {
            idToPlatform.put(platform.getId(), platform);
        }
        return idToPlatform;
    }

    public Map<Long, List<PlatformToUser>> getUserIdtoPlatformToUser() {
        HashMap<Long, List<PlatformToUser>> userIdToPlatformToUser = new HashMap<>();
        for (PlatformToUser platformToUser : platformToUsers()) {
            if(userIdToPlatformToUser.containsKey(platformToUser.getUserId())) {
                userIdToPlatformToUser.get(platformToUser.getUserId()).add(platformToUser);
            } else {
                List<PlatformToUser> platformsToUser = new ArrayList<>();
                platformsToUser.add(platformToUser);
                userIdToPlatformToUser.put(platformToUser.getUserId(), platformsToUser);
            }
        }
        return userIdToPlatformToUser;
    }
}
