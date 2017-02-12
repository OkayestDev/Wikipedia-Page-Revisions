package edu.bsu.cs222.wikipagerevisions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class revisionsParser {
    private List<Revisions> revisionsList = new ArrayList<>();
    private List<Revisions> uniqueUserRevisions = new ArrayList<>();

    public List<Revisions> parseRevisions(Document doc) {
        NodeList numberOfRevisions = doc.getElementsByTagName("rev");
        for (int i = 0; i < numberOfRevisions.getLength(); i++) {
            addRevisionToList(numberOfRevisions.item(i));
        }
        sortUniqueUsersByRevisionCount();
        return revisionsList;
    }

    private void addRevisionToList(Node revision) {
        Element temp = (Element) revision;
        String user = temp.getAttribute("user");
        handleUniqueUsersRevisions(user);
        String timestamp = temp.getAttribute("timestamp");
        Revisions rev = new Revisions(user, timestamp);
        revisionsList.add(rev);
    }

    private void handleUniqueUsersRevisions(String user) {
        if (isUniqueUser(user)) {
            uniqueUserRevisions.add(new Revisions(user, ""));
        }
        else {
            for (Revisions rev : uniqueUserRevisions) {
                if (rev.getUser().equals(user)) {
                    rev.increaseRevisionsCount();
                }
            }
        }
    }

    public boolean isUniqueUser(String user) {
        for (Revisions rev: revisionsList) {
            if (rev.getUser().equals(user)) {
                return false;
            }
        }
        return true;
    }

    public void sortUniqueUsersByRevisionCount() {
        int count = 0;
        for (int i = 0; i < uniqueUserRevisions.size(); i++) {
            int max = uniqueUserRevisions.get(i).getRevisionsCount();
            int indexOfMax = i;
            for (int j = i; j < uniqueUserRevisions.size(); j++) {
                int currentRevisionCount = uniqueUserRevisions.get(j).getRevisionsCount();
                if (max < currentRevisionCount) {
                    max = currentRevisionCount;
                    indexOfMax = j;
                }
            }
            if (indexOfMax != i) {
                Revisions temp = uniqueUserRevisions.get(indexOfMax);
                uniqueUserRevisions.remove(indexOfMax);
                uniqueUserRevisions.add(count, temp); //appends to Front?
                count++;
            }
        }
    }

    public List<Revisions> getUniqueUserRevisionsList() {
        return uniqueUserRevisions;
    }
}
