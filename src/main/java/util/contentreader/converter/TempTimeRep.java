package util.contentreader.converter;

import objects.dbentities.TimeToSend;

public class TempTimeRep {
    private static long id = 0l;

    public TimeToSend save(TimeToSend timeToSend) {
        timeToSend.setId(id);
        id ++;
        return timeToSend;
    }
}
