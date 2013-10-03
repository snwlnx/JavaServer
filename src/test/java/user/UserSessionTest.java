package user;

import base.LongId;
import base.Player;
import org.junit.Assert;
import org.junit.Test;

public class UserSessionTest extends Assert {

	@Test
	public void testUserName() {
		UserSession session = new UserSession("user0");

		assertTrue("user0".equals(session.getUserName()));
	}

	@Test
	public void setUserIdTest() {
		UserSession session = new UserSession("user1");
		session.setUserId(new LongId<User>(1));
		assertNotNull(session.getLastTime());
	}

	@Test
	public void playerTest() {
		UserSession session = new UserSession("user2");
		Player player = new Player();

		session.setPlayer(player);
		assertTrue(player == session.getPlayer());
	}

	@Test
	public void hasStateTest() {
		UserSession session = new UserSession("user3");
		assertFalse(session.hasState());
	}
}
