package dao;

import objects.dbentities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDao {

    @PersistenceContext
    EntityManager entityManager;

    public List<User> getAllUsers() {
        return
                entityManager.createQuery(
                        "select u from user u", User.class)
                        .getResultList();
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
