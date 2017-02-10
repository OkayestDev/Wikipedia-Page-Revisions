package edu.bsu.cs222.wikipagerevisions;

public class Revisions {
    private String user;
    private String timestamp;
    int revisionsCount;

    public Revisions(String user, String timestamp) {
        this.user = user;
        revisionsCount = 1;
        this.timestamp = localizeTimestamp(timestamp);
    }

    private String localizeTimestamp(String timestamp) {
        //Finish this method, need to change UTC (ZULU)to EST
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getRevisionsCount() {
        return revisionsCount;
    }

    @Override
    public String toString() {
        return "User: " + user + " Timestamp: " + timestamp + " Count: " + revisionsCount;
    }

    public void increaseRevisionsCount() {
        revisionsCount++;
    }
}
