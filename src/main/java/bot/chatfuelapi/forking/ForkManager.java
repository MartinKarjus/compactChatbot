package bot.chatfuelapi.forking;


import db.repository.TransmissionLogRepository;
import objects.dbentities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForkManager {

    @Autowired
    private TransmissionLogRepository transmissionLogRepository;

//    public Question getNextQuestionForUserAtEndOfContent() {
//
//    }


}
