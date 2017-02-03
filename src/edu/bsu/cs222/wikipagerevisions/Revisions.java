package edu.bsu.cs222.wikipagerevisions;

public class Revisions {
    private String comment;
    private String user;
    private String timestamp; //localize to my timezone

    public void setInformation(String user, String comment, String timestamp)
    {
        this.user = user;
        this.comment = comment;
        this.timestamp = localizeTimestamp(timestamp);
    }

    private String localizeTimestamp(String timestamp)
    {
        //dunno.
        return "";
    }

    @Override
    public String toString()
    {
        return user + " " + timestamp + " " + comment;
    }
}
