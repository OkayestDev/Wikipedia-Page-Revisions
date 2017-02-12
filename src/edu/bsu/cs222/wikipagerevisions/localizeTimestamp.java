package edu.bsu.cs222.wikipagerevisions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class localizeTimestamp {
    public String localizeTime(String timestamp) {
        try {
            TimeZone local = TimeZone.getDefault();
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd\tHH:mm:ss");
            Date revisionDate = dateFormatter.parse(timestamp);
            dateFormatter.setTimeZone(local);
            TimeZone.setDefault(local);
            return dateFormatter.format(revisionDate);
        }
        catch(ParseException e) {
            return null;
        }
    }

    public String reformatTimestamp(String timestamp) {
        timestamp = timestamp.replace('T', '\t');
        timestamp = timestamp.substring(0, timestamp.length() - 1);
        return timestamp;
    }
}
