package edu.bsu.cs222.wikipagerevisions;

public class Revisions {
    private String user;
    private String timestamp;
    private int revisionsCount;

    public Revisions(String user, String timestamp) {
        localizeTimestamp localize = new localizeTimestamp();
        this.user = user;
        revisionsCount = 1;
        if (!timestamp.equals("")) {
            this.timestamp = localize.reformatTimestamp(timestamp);
            this.timestamp = localize.localizeTime(this.timestamp);
        }
    }

    @Override
    public String toString() {
        return "User: " + user + " Timestamp: " + timestamp + " Count: " + revisionsCount;
    }

    void increaseRevisionsCount() {
        revisionsCount++;
    }

    //TableView of the GUI uses all of the methods below. Compiler doesn't recognize it.
    @SuppressWarnings("unused")
    public int getRevisionsCount() {
        return revisionsCount;
    }

    @SuppressWarnings("unused")
    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }
}
