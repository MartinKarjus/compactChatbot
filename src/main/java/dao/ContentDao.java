package dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ContentDao {

    @PersistenceContext
    EntityManager entityManager;

//    public List<Content> getContentForDay(List<Long> daysForUsergroups, List<Usergroup> usergroups, List<BotUser> botUsers) {
//        List<Long> ids = usergroups
//                .stream()
//                .map(Usergroup::getId)
//                .collect(Collectors.toList());
//
//        System.out.println("------------");
//        System.out.println(daysForUsergroups);
//        System.out.println(ids);
//        System.out.println("------------");
//
//        TypedQuery<Content> query = entityManager.createQuery(
//                "select c from Content c, Plan p, Usergroup ug " +
//                        "where p.contentId = c.id " +
//                        "and p.day in :days " +
//                        "and ug.id in :usergroupIds " +
//                        "and ug.id = p.usergroupId",
//                Content.class
//        );
//        query.setParameter("days", daysForUsergroups);
//        query.setParameter("usergroupIds", ids);
//
//        return query.getResultList();
//    }


}
