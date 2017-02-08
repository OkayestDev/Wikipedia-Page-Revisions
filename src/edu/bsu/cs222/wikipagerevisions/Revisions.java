package edu.bsu.cs222.wikipagerevisions;

public class Revisions {
    private String user;
    private String timestamp;
    private boolean unique = false;

    public Revisions(String user, String timestamp) {
        this.user = user;
        this.timestamp = localizeTimestamp(timestamp);
    }

    private String localizeTimestamp(String timestamp) {
        //Finish this method, need to change EST to UTC (Zulu)
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "User: " + user + " Timestamp: " + timestamp;
    }
}
