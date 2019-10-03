package util.contentreader.converter.general;

import db.repository.CompanyRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Company;
import objects.dbentities.Plan;
import objects.dbentities.TimeToSend;
import util.contentreader.dataclasses.ContentOptions;

public class OptionHandler {

    TimeHandler timeHandler = new TimeHandler();
    TimeToSendRepository timeToSendRepository;
    CompanyRepository companyRepository;

    public OptionHandler(TimeToSendRepository timeToSendRepository, CompanyRepository companyRepository) {
        this.timeToSendRepository = timeToSendRepository;
        this.companyRepository = companyRepository;
    }

    private long getCompanyIdForNameOrCreateNew(String companyName) {
        Company c = companyRepository.findByName(companyName);
        if(c == null) {
            c = new Company();
            c.setName(companyName);
            c = companyRepository.save(c);
            //todo log creation of new company instead of printing..
            System.out.println("Creating new company: " + c.getName() + " ,id: " + c.getId());
            return c.getId();
        } else {
            return c.getId();
        }
    }

    public void handleOptions(ContentOptions options, Plan currentPlan) {

        if (options.getOptions().containsKey("timetosend")) {
            TimeToSend timeToSend = timeHandler.getTimeToSend(options.getOptions().get("timetosend").get(1));
            //todo check for existing values in the DB and use those instead of making a new one each time
            timeToSend = timeToSendRepository.save(timeToSend);
            currentPlan.setTimeToSendId(timeToSend.getId());
        }
        if (options.getOptions().containsKey("daytosend")) {
            currentPlan.setDay(Long.valueOf(options.getOptions().get("daytosend").get(1)));
        }
        if (options.getOptions().containsKey("company")) {
            currentPlan.setCompanyId(getCompanyIdForNameOrCreateNew(options.getOptions().get("company").get(1)));
        }
        if (options.getOptions().containsKey("outsideworkhours")) {
            //todo implement
        }
        if (options.getOptions().containsKey("description")) {
            currentPlan.setDescription(options.getOptions().get("description").get(1));
        }
        if (options.getOptions().containsKey("namedmessage")) {
            currentPlan.setNamedMessage(options.getOptions().get("namedmessage").get(1));
        }
    }

}
