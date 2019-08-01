package dao;

import objects.shared.BotUser;
import objects.shared.Usergroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class BotUserDao {

    @PersistenceContext
    EntityManager entityManager;


    public List<BotUser> getAllUsers() {
        return entityManager.createQuery("select u from BotUser u", BotUser.class).getResultList();
    }

    public List<BotUser> getAllUsersFromPlatform(String platformName) {
        TypedQuery<BotUser> query = entityManager.createQuery(
                "select bu from BotUser bu, Platform p, Usergroup ug, PlatformToUsergroup pToUg " +
                        "where bu.usergroupId = ug.id and p.id = pToUg.platformId and ug.id = pToUg.usergroupId and p.name = :platformName",
                BotUser.class);

        query.setParameter("platformName", platformName);

        return query.getResultList();
    }

    public List<Usergroup> getUsergroupsByUsers(List<BotUser> users) {
        List<Long> ids = users
                .stream()
                .map(BotUser::getId)
                .collect(Collectors.toList());

        TypedQuery<Usergroup> query = entityManager.createQuery(
                "select ugroup from Usergroup ugroup " +
                        "where ugroup.id in :ids",
                Usergroup.class
        );
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}
