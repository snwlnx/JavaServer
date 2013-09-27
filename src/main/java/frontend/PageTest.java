package frontend;

import base.LongId;
import junit.framework.Assert;
import user.UserSession;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 29.03.13
 * Time: 2:40
 * To change this template use File | Settings | File Templates.
 */
public class PageTest {

    LongId<UserSession> sessionId = new LongId<UserSession>(1);
    String userName = "Alex";

    Page page = new Page();

    String pageStart ="<body><p>sessionId 1</p><form id=\"MainForm\" method=\"POST\">" +
            "<label>UserName <input type=\"text\" name=\"user_name\" /></label><input type=\"submit\" value=\"Submit\" /></form></body>";

    String pageWait ="<body><p>sessionId 1</p><p>userName Alex</p><p>Wait for auth!!!</p><form id=\"MainForm\" method=\"POST\">" +
            "<input type=\"hidden\" name=\"session\" value=\"1\" /></form></body>";


    @org.junit.Test
    public void testStart() throws Exception {
        page.Start(sessionId);
        Assert.assertEquals(page.Start(sessionId),pageStart);
    }

    @org.junit.Test
    public void testWaitForAuthorization() throws Exception {
       Assert.assertEquals(pageWait,page.WaitForAuthorization(sessionId, userName));

    }
}
