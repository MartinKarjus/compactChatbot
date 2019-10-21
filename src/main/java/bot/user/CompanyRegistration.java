package bot.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import db.repository.BotUserRepository;
import db.repository.CompanyRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import objects.dbentities.BotUser;
import objects.dbentities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyRegistration {

    public static final String TESTERS_COMPANY = "testers";

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    BotUserRepository botUserRepository;

    public void regUserToCompany(BotUser user, String companyName) {
        Company c = companyRepository.findByName(companyName);
        if(c != null) {
            user.setCompanyId(c.getId());
        } else {
            c = new Company();
            c.setName(companyName);
            c = companyRepository.save(c);
            user.setCompanyId(c.getId());
        }
    }

    public void regUserToExistingCompany(BotUser user, String companyName) {
        Company c = companyRepository.findByName(companyName);
        if(c != null) {
            user.setCompanyId(c.getId());
        } else {
            throw new RuntimeException("Trying to save user to company that isn't registered: " + companyName);
        }
    }

    public void regTestUser(BotUser user) {
        Company c = companyRepository.findByName(TESTERS_COMPANY);
        if(c != null) {
            user.setCompanyId(c.getId());
        } else {
            try { //todo remove after conforming it works
                c = new Company();
                c.setName(TESTERS_COMPANY);
                c = companyRepository.save(c);
                user.setCompanyId(c.getId());
                throw new RuntimeException("Testers company doesn't exist, creating..");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }


}
