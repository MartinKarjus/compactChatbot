package util.contentreader.converter.general.contentbasehandler;

import objects.dbentities.TimeToSend;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TimeHandler {

    private List<String> timePresets = new ArrayList<>(Arrays.asList("morning", "afternoon", "evening", "never", "always"));

    public TimeToSend getTimeToSend(String timeAsString) {
        TimeToSend timeToSend = new TimeToSend();

        if (timePresets.contains(timeAsString)) { //todo improve this, confirm times..
            switch (timeAsString) {
                case "morning":
                    timeAsString = "09:00:00";
                    break;
                case "afternoon":
                    timeAsString = "14:30:00";
                    break;
                case "evening":
                    timeAsString = "16:30:00";
                    break;
                case "never":
                    timeToSend.setNeverSend(true);
                    return timeToSend;
                case "always":
                    timeToSend.setAlwaysSend(true);
                    return timeToSend;
                default:
                    throw new IllegalArgumentException("Unhandled time as string:" + timeAsString);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(timeAsString);
            timeToSend.setTimeToSend(new Timestamp(parsedDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeToSend;
    }
}
