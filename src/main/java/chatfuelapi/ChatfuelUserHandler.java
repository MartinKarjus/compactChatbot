package chatfuelapi;

import dao.ActionPlanDao;
import dao.ChatfuelUserDao;
import objects.ActionPlan;
import objects.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ChatfuelUserHandler {

    @Autowired
    ChatfuelUserDao chatfuelUserDao;
    @Autowired
    ActionPlanDao actionPlanDao;

    private List<Person> chatfuelUsers;
    private Map<Integer, ActionPlan> planForGroup;


    private void refreshUsersList() {
        chatfuelUsers = chatfuelUserDao.getChatFuelUsers();
    }

    private void checkUser(Person p) {

    }

    public void updateUsers() {
        refreshUsersList();
        for (Person user : chatfuelUsers) {
            checkUser(user);
        }
    }
}
