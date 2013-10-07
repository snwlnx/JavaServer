package frontend;

import base.LongId;
import frontend.Page;
import game.ChatMessage;
import user.User;
import user.UserSession;
import org.junit.Test;
import org.junit.Assert;

public class PageTest extends Assert {

    LongId<UserSession> sessionId1 = new LongId<UserSession>(1);
	LongId<UserSession> sessionId2 = new LongId<UserSession>(1);

	LongId<User> userId1 = new LongId<User>(2);
	LongId<User> userId2 = new LongId<User>(2);

    String userName1 = "Alex";
	String userName2 = "Alex";

    @Test
    public void testStart() {
        assertEquals(Page.Start(sessionId1), Page.Start(sessionId2));
    }

	@Test
	public void testFinishLose() {
		assertEquals(Page.FinishLose(sessionId1), Page.FinishLose(sessionId2));
	}

	@Test
	public void testFinishWin() {
		assertEquals(Page.FinishWin(sessionId1), Page.FinishWin(sessionId2));
	}

	@Test
	public void testWaitForAuthorization() {
		assertEquals(Page.WaitForAuthorization(sessionId1, userName1), Page.WaitForAuthorization(sessionId2, userName2));
	}

	@Test
	public void testAuthAccept() {
		assertEquals(Page.AuthAccept(sessionId1, userName1, userId1), Page.AuthAccept(sessionId2, userName2, userId2));
	}

	@Test
	public void playJqueryTest() {
		assertEquals(Page.PlayJQuery(sessionId1, userName1), Page.PlayJQuery(sessionId2, userName2));
	}


	@Test
	public void PlayTest() {
		assertEquals(Page.Play(sessionId1, userName1), Page.Play(sessionId2, userName2));
	}

	@Test
	public void notAuthTest() {
		assertEquals(Page.NotAuthorized(sessionId1, userName1), Page.NotAuthorized(sessionId2, userName2));
	}
}
