package db.dao;

import objects.dbentities.User;
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

    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT a from User a"
                , User.class);

        //System.out.println(query.getResultList());
        return query.getResultList();
    }

    public Map<Long, User> getAllUsersById() {
        HashMap<Long, User> idToUser = new HashMap<>();
        for (User user : getAllUsers()) {
            idToUser.put(user.getId(), user);
        }
        return idToUser;
    }

    @Transactional
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }


//    public List<User> getAllUsersFromPlatform(String platformName) {
//        TypedQuery<User> query = entityManager.createQuery(
//                "select bu from User bu, Platform p, Usergroup ug, PlatformToUsergroup pToUg " +
//                        "where bu.usergroupId = ug.id and p.id = pToUg.platformId and ug.id = pToUg.usergroupId and p.name = :platformName",
//                User.class);
//
//        query.setParameter("platformName", platformName);
//
//        return query.getResultList();
//    }

//    public List<Usergroup> getUsergroupsByUsers(List<User> users) {
//        List<Long> ids = users
//                .stream()
//                .map(User::getId)
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
