package edu.bsu.cs222.wikipagerevisions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Revisions> revisionsList = new ArrayList<>();
    private List<Revisions> uniqueUserRevisions = new ArrayList<>();
    private Document doc;
    private URL url;

    public void executeModel(String search) {
        url = loadURL(search);
        if (isGoodConnection()) {
            doc = URLtoDoc();
            parseRevisions();
        }
    }

    public void clear() {
        revisionsList = new ArrayList<>();
        uniqueUserRevisions = new ArrayList<>();
    }

    public URL loadURL(String search) {
        String URLStart = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=revisions&titles=";
        String URLEnd = "&rvprop=timestamp|comment|user&rvlimit=30&redirects";
        search = search.replace(" ", "_");
        search = URLStart + search + URLEnd;
        try {
            URL url = new URL(search);
            url.openStream();
            return new URL(search);
        }
        catch(Exception e) {
            return null;
        }
    }

    private Document URLtoDoc() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(url.openStream());
        }
        catch(Exception e) {
            return null;
        }
    }

    public List<Revisions> parseRevisions() {
        if (doesPageExist() && doesPageHaveRevisions()) {
            NodeList numberOfRevisions = doc.getElementsByTagName("rev");
            for (int i = 0; i < numberOfRevisions.getLength(); i++) {
                addRevisionToList(numberOfRevisions.item(i));
            }
        }
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

    private boolean isUniqueUser(String user) {
        for (Revisions rev: revisionsList) {
            if (rev.getUser().equals(user)) {
                return false;
            }
        }
        return true;
    }

    public boolean doesPageExist() {
        NodeList check = doc.getElementsByTagName("page");
        Element idx = (Element) check.item(0);
        return !(idx.getAttribute("_idx").equals("-1"));
    }

    public boolean doesPageHaveRevisions() {
        NodeList check = doc.getElementsByTagName("rev");
        return check.getLength() >= 1;
    }

    public boolean isRedirection() {
        NodeList check = doc.getElementsByTagName("redirects");
        return check.getLength() >= 1;
    }

    public boolean isGoodConnection() {
        try {
            url.openConnection();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public String getRedirection() {
        if (isRedirection()) {
            NodeList redirection = doc.getElementsByTagName("r");
            Element temp = (Element) redirection.item(0);
            return "Redirected: " + temp.getAttribute("from") + " to " + temp.getAttribute("to");
        }
        return "";
    }

    public List<Revisions> getRevisionsList() {
        return revisionsList;
    }

    public List<Revisions> getUniqueUserRevisionsList() {
        return uniqueUserRevisions;
    }

    //Test purposes only
    public void setDoc(Document doc) {
        this.doc = doc;
    }
}