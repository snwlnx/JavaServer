package game;

import base.Fireball;
import base.LongId;
import base.Player;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import user.User;

import java.util.LinkedList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameSessionTest extends Assert {

	private static GameSession gameSession;
	private static LongId<User> userId, victimId;
	private static final String message = "Test message.";

	@BeforeClass
	public static void initGameSession() {
		userId = new LongId<User>(111);
		victimId = new LongId<User>(999);

		gameSession = new GameSession(userId);
		gameSession.addNewPlayer(userId);
	}

	@Test
	public void aGameSessionConstruct() {
		assertEquals(1, gameSession.getAllUserId().size());
		assertTrue(gameSession.getAllUserId().contains(userId));
	}

	@Test
	public void bAddUserToGamesessionTest() {
		Player player = gameSession.addNewPlayer(victimId);

		assertNotNull(player);
		assertTrue(gameSession.getAllUserId().contains(victimId));

		assertEquals(player, gameSession.getPlayer(victimId));
		assertEquals(2, gameSession.getAllUserId().size());
	}

	@Test
	public void cChatMessagesTest() {
		gameSession.addMessageToChat(userId, message);
		ChatMessage[] msgs = gameSession.getMessagesForUser(userId);
		assertEquals(1, msgs.length);

		gameSession.addMessageToChat(victimId, message);
		msgs = gameSession.getMessagesForUser(userId);
		assertEquals(1, msgs.length);
	}


	@Test
	public void eChatMessageEmptyTest() {
		assertNull(gameSession.getMessagesForUser(victimId));
	}

	@Test
	public void doubleAddToGameTest() {
		assertNull(gameSession.addNewPlayer(userId));
	}

	@Test
	public void addFireballTest() {

		LinkedList<Fireball> list = new LinkedList<Fireball>();
		list.add(new Fireball(0,0, 10, 10));

		gameSession.setFireballs(list);
		assertTrue(gameSession.getFireballs().equals(list));
	}
}
