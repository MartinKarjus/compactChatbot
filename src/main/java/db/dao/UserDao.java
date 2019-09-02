package db.dao;

import objects.dbentities.BotUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<BotUser> getAllUsers() {
        TypedQuery<BotUser> query = entityManager.createQuery(
                "SELECT a from BotUser a"
                , BotUser.class);

        //System.out.println(query.getResultList());
        return query.getResultList();
    }

    public Map<Long, BotUser> getAllUsersById() {
        HashMap<Long, BotUser> idToUser = new HashMap<>();
        for (BotUser user : getAllUsers()) {
            idToUser.put(user.getId(), user);
        }
        return idToUser;
    }

    @Transactional
    public BotUser addUser(BotUser user) {
        entityManager.persist(user);
        return user;
    }


//    public List<BotUser> getAllUsersFromPlatform(String platformName) {
//        TypedQuery<BotUser> query = entityManager.createQuery(
//                "select bu from BotUser bu, Platform p, Usergroup ug, PlatformToUsergroup pToUg " +
//                        "where bu.usergroupId = ug.id and p.id = pToUg.platformId and ug.id = pToUg.usergroupId and p.name = :platformName",
//                BotUser.class);
//
//        query.setParameter("platformName", platformName);
//
//        return query.getResultList();
//    }

//    public List<Usergroup> getUsergroupsByUsers(List<BotUser> users) {
//        List<Long> ids = users
//                .stream()
//                .map(BotUser::getId)
//                .collect(Collectors.toList());
//
//        TypedQuery<Usergroup> query = entityManager.createQuery(
//                "select ugroup from Usergroup ugroup " +
//                        "where ugroup.id in :ids",
//                Usergroup.class
//        );
//        query.setParameter("ids", ids);
//        return query.getResultList();
//    }

}
