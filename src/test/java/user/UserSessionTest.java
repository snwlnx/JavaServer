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
		UserSession userSession = new UserSession("user0");
		assertTrue("user0".equals(userSession.getUserName()));
	}

    @Test
    public void playerTest() {
        UserSession userSession = new UserSession("user2");
        Player player = new Player();

        userSession.setPlayer(player);
        assertTrue(player == userSession.getPlayer());
    }

    @Test
	public void setUserIdTest() {
		UserSession userSession = new UserSession("user1");
		userSession.setUserId(new LongId<User>(1));
		assertNotNull(userSession.getLastTime());
	}

	@Test
	public void hasStateTest() {
		UserSession userSession = new UserSession("user3");
		assertFalse(userSession.hasState());
	}

	@Test
	public void stateTest() {
		UserSession userSession = new UserSession("user5");
		assertFalse(userSession.hasState());

		userSession.updateUserState(new StateAuthorized());
		assertTrue(userSession.hasState());
	}

    @Test
    public void gameSessionsTest() {
        UserSession userSession = new UserSession("user4");
        Set<LongId<GameSession>> set = new HashSet<LongId<GameSession>>();
        set.add(new LongId<GameSession>(1));

        userSession.setAvailableGameSessions(set);
        assertEquals(userSession.getAvailableGameSessions(), set);
    }

    @Test
	public void enemiesFireballTest() {
		UserSession userSession = new UserSession("user6");
		LinkedList<Fireball> fireballList = new LinkedList<Fireball>();
		fireballList.add(new Fireball(0,0,1,0));

		LinkedList<Player> players = new LinkedList<Player>();
		players.add(new Player(0,0,1,0));

		userSession.setFireballs(fireballList);
		userSession.setEnemies(players);

		assertEquals(fireballList, userSession.getFireballs());
		assertEquals(players, userSession.getEnemies());
	}
}
