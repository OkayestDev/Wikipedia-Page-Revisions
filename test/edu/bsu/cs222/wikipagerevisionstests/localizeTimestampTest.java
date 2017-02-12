package edu.bsu.cs222.wikipagerevisionstests;
import edu.bsu.cs222.wikipagerevisions.localizeTimestamp;

import org.junit.Assert;
import org.junit.Test;

import java.util.TimeZone;

public class localizeTimestampTest {
    private localizeTimestamp test = new localizeTimestamp();

    @Test
    public void testLocalizeTimeWithBadFormat() {
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        String badFormattedTimeStamp = "2016/12/23 16.25.19";
        Assert.assertEquals(null, test.localizeTime(badFormattedTimeStamp));
    }

    @Test
    public void testLocalizeTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        String testTimestamp = "2016-12-23\t16:25:19";
        String result = "2016-12-23\t11:25:19";
        Assert.assertEquals(result, test.localizeTime(testTimestamp));
    }

    @Test
    public void testReformatTimestamp() {
        String testTimestamp = "2016-12-23T16:25:19Z";
        String result = "2016-12-23\t16:25:19";
        Assert.assertEquals(result, test.reformatTimestamp(testTimestamp));
    }
}
