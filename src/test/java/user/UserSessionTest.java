package user;

import base.Fireball;
import base.LongId;
import base.Player;
import game.GameSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

	@Test
	public void gameSessionsTest() {
		UserSession session = new UserSession("user4");
		Set<LongId<GameSession>> set = new HashSet<LongId<GameSession>>();
		set.add(new LongId<GameSession>(1));

		session.setAvailableGameSessions(set);
		assertEquals(session.getAvailableGameSessions(), set);
	}

	@Test
	public void stateTest() {
		UserSession session = new UserSession("user5");
		assertFalse(session.hasState());

		session.updateUserState(new StateAuthorized());
		assertTrue(session.hasState());
	}

	@Test
	public void enemiesFireballTest() {
		UserSession session = new UserSession("user6");
		LinkedList<Fireball> fireballList = new LinkedList<Fireball>();
		fireballList.add(new Fireball(0,0,1,0));

		LinkedList<Player> players = new LinkedList<Player>();
		players.add(new Player(0,0,1,0));

		session.setFireballs(fireballList);
		session.setEnemies(players);

		assertEquals(fireballList, session.getFireballs());
		assertEquals(players, session.getEnemies());
	}
}
