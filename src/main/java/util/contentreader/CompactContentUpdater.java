package util.contentreader;

import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.contentreader.converter.ConvertResult;
import util.contentreader.converter.GeneralConverter;
import util.contentreader.dataclasses.ContentBase;
import util.contentreader.dbadjuster.ContentToDb;

import java.io.IOException;
import java.util.List;

@Component
public class CompactContentUpdater {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TimeToSendRepository timeToSendRepository;

    @Autowired
    private ContentToDb contentToDb;

    public static void main(String[] args) throws IOException {
        new CompactContentUpdater().updateContent();

    }

    public void updateContent() {
        try {
            List<List<List<ContentBase>>> plans;
            plans = new ContentReader().readAndUpdate(null);
            ConvertResult convertResult = new GeneralConverter(questionRepository, timeToSendRepository).convertPlans(plans);
            contentToDb.setConvertResult(convertResult);
            contentToDb.saveContentToQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
