package util.contentreader;

import db.repository.CompanyRepository;
import db.repository.PlanRepository;
import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.contentreader.converter.ConvertResult;
import util.contentreader.converter.general.GeneralConverter;
import util.contentreader.dataclasses.ContentBase;
import util.contentreader.dbadjuster.ContentToDb;

import java.io.IOException;
import java.util.List;

@Component
public class CompactContentUpdater {

    public static final String BOT_CONTENT_FOLDER = "/botcontent/";
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TimeToSendRepository timeToSendRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ContentToDb contentToDb;

//    private Question findQuestionInQuestionTreeThatIsNotSubQuestionOfQuestion(Question subQuestion) {
//
//    }

    private void printPlans() {

    }

    public void updateContent() {
        try {
            List<List<List<ContentBase>>> plansAsContentBase;

//            String fileName = "/botcontent/Chatbot flow new - Sheet1.csv";
            List<String> contentFiles = new FolderReader().getAllContentFileNames();
            for (String contentFile : contentFiles) {
                plansAsContentBase = new ContentReader().readAndUpdate(BOT_CONTENT_FOLDER + contentFile);
                List<Plan> plans = new GeneralConverter(questionRepository, timeToSendRepository, companyRepository, planRepository).convertPlans(plansAsContentBase);
                printPlans();
//                contentToDb.setConvertResult(convertResult);
//                contentToDb.saveContentToQuestions();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
