package util.contentreader.converter;

import objects.dbentities.Question;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempQuestionRep {
    private static Long counter = 0L;

    public Question save(Question question) {
        question.setId(counter);
        counter++;
        return question;
    }


}
