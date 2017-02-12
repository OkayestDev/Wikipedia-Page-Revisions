package edu.bsu.cs222.wikipagerevisions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Revisions {
    private String user;
    private String timestamp;
    private int revisionsCount;

    public Revisions(String user, String timestamp) {
        this.user = user;
        revisionsCount = 1;
        if (!timestamp.equals("")) {
            this.timestamp = reformatTimestamp(timestamp);
        }
    }

    private String localizeTimestamp(String timestamp) {
        try {
            TimeZone local = TimeZone.getDefault();
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd\tHH:mm:ss");
            Date revisionDate = dateFormatter.parse(timestamp);
            dateFormatter.setTimeZone(local);
            TimeZone.setDefault(local);
            return dateFormatter.format(revisionDate);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String reformatTimestamp(String timestamp) {
        timestamp = timestamp.replace('T', '\t');
        timestamp = timestamp.substring(0, timestamp.length() - 1);
        return localizeTimestamp(timestamp);
    }

    String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "User: " + user + " Timestamp: " + timestamp + " Count: " + revisionsCount;
    }

    void increaseRevisionsCount() {
        revisionsCount++;
    }
}
