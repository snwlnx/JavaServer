package game;

import base.Fireball;
import base.LongId;
import base.ResourceSystem;
import frontend.FrontendImpl;
import message.MessageSystemImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import resource.ResourceSystemImpl;
import user.User;

import java.util.Random;

public class GameServiceTest extends Assert {

	private static GameServiceImpl gameService;
	private static LongId<User> userId, victimId;
	private LongId<GameSession> gameId;


	@BeforeClass
	public static void initGameService() {
		MessageSystemImpl messageSystem = new MessageSystemImpl();
		FrontendImpl frontend = new FrontendImpl(messageSystem);

		userId = new LongId<User>(new Random().nextLong());
		victimId = new LongId<User>(new Random().nextLong());

		gameService = new GameServiceImpl(messageSystem, new ResourceSystemMock());
	}

	@Test
	public void startGameTest() {
		gameId = gameService.startGame(userId);
		assertNotNull(gameId);
	}

	@Test
	public void joinToGameTest() {
		gameService.joinToGame(victimId, gameId);
	}

	@Test
	public void addFireballTest() {
		gameService.addFireball(new LongId<User>(new Random().nextLong()), new Fireball(0,0,1,1));
	}


}
