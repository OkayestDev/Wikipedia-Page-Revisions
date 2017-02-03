package edu.bsu.cs222.wikipagerevisions;

import java.util.ArrayList;
import java.util.List;

public class Revisions {
    String comment;
    String user;
    String timestamp; //localize to my timezone
    List<Revisions> revisionList = new ArrayList<Revisions>();

    public void add(user, comment, timestamp)
    {

    }

    String getRevisionList()
    {
        return revisionList;
    }
}
