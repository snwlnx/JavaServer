package game;

import base.Fireball;
import base.LongId;
import base.ResourceSystem;
import frontend.FrontendImpl;
import message.MessageSystemImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import resource.ResourceSystemImpl;
import user.User;

import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameServiceTest extends Assert {

	private static GameServiceImpl gameService;
	private static LongId<User> userId, victimId;
	private static LongId<GameSession> gameId;


	@BeforeClass
	public static void initGameService() {
		MessageSystemImpl messageSystem = new MessageSystemImpl();
		FrontendImpl frontend = new FrontendImpl(messageSystem);

		userId = new LongId<User>(111);
		victimId = new LongId<User>(999);

		gameService = new GameServiceImpl(messageSystem, new ResourceSystemMock());
	}

	@Test
	public void aStartGameTest() {
		gameId = gameService.startGame(userId);
		assertNotNull(gameId);
	}

	@Test
	public void bJoinToGameTest() {
		assertTrue(gameService.joinToGame(victimId, gameId));
	}

	@Test
	public void cAddFireballTest() {
		gameService.addFireball(userId, new Fireball(0,0,1,1));
	}


}
