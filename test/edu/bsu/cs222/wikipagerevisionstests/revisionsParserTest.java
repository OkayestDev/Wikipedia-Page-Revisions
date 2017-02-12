package edu.bsu.cs222.wikipagerevisionstests;
import edu.bsu.cs222.wikipagerevisions.*;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class revisionsParserTest {
    private revisionsParser test = new revisionsParser();

    private Document openXMLFile() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File("test-assets/Soup.xml"));
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testParseRevisions() {
        Document doc = openXMLFile();
        Revisions rev = new Revisions("Northamerica1000", "2016-12-23T16:25:19Z");
        String parsedRevisions = test.parseRevisions(doc).get(1).toString();
        String testString = rev.toString();
        Assert.assertTrue(testString.equals(parsedRevisions));
    }

    @Test
    public void testIsUniqueUserFalse() {
        Document doc =openXMLFile();
        test.parseRevisions(doc);
        Assert.assertFalse(test.isUniqueUser("Northamerica1000"));
    }

    @Test
    public void testIsUniqueUserTrue() {
        Document doc = openXMLFile();
        test.parseRevisions(doc);
        Assert.assertTrue(test.isUniqueUser("HeebeeGeebee"));
    }

    @Test
    public void testSortUniqueUsersByRevisionCount() {
        Document doc = openXMLFile();
        test.parseRevisions(doc);
        test.sortUniqueUsersByRevisionCount();
        String revToString = "User: Northamerica1000 Timestamp: null Count: 23";
        Assert.assertEquals(revToString, test.getUniqueUserRevisionsList().get(0).toString());
    }

    @Test
    public void testRevisionCounter() {
        Document doc = openXMLFile();
        test.parseRevisions(doc);
        Assert.assertEquals(23, test.getUniqueUserRevisionsList().get(0).getRevisionsCount());
    }
}
