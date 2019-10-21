package bot.user;


import db.repository.BotUserRepository;
import db.repository.CompanyRepository;
import db.repository.QuestionGroupRepository;
import objects.dbentities.BotUser;
import objects.dbentities.Company;
import objects.dbentities.QuestionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class GroupRegistration {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    QuestionGroupRepository questionGroupRepository;

    @Autowired
    BotUserRepository botUserRepository;

    public void regUserToGroup(BotUser user, Long companyId) {
        List<QuestionGroup> questionGroups = questionGroupRepository.findByCompanyId(companyId);

        if(questionGroups == null) {
            throw new RuntimeException("test to see if this can happen"); // todo rmv
        }

        if(questionGroups.size() > 1) {
            throw new RuntimeException("For some reason we have more than 1 questionGroup per company, this isn't supported yet.");
        } else if (questionGroups.size() == 1) {
            user.setQuestionGroupId(questionGroups.get(0).getId());
        } else {
            QuestionGroup questionGroup = new QuestionGroup();
            questionGroup.setCompanyId(companyId);
            questionGroup.setActive(true);
            questionGroup.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
            questionGroup.setName("i wonder why this has a not null constraint..");

            questionGroup = questionGroupRepository.save(questionGroup);

            user.setQuestionGroupId(questionGroup.getId());
        }
    }



}
