package game;

import base.*;
import frontend.FrontendImpl;
import frontend.MessageRefreshPosition;
import message.MessageSystemImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import resource.ResourceSystemImpl;
import user.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameServiceTest extends Assert {

	private static GameServiceImpl gameService;
	private static Frontend frontend;
	private static  MessageSystemImpl messageSystem;
	private static LongId<User> userId, victimId;
	private static LongId<GameSession> gameId;


	@BeforeClass
	public static void initGameService() {
		messageSystem = new MessageSystemImpl();
		frontend = new FrontendMock(messageSystem);

		userId = new LongId<User>(111);
		victimId = new LongId<User>(999);

		gameService = new GameServiceImpl(messageSystem, new ResourceSystemMock());
	}

	@Test
	public void joinToEmtyGame() {
//		boolean joined = gameService.joinToGame(new LongId<User>(1), new LongId<GameSession>(9999));
//		assertFalse(joined);
	}

	@Test
	public void runTest() {
		Thread gm = new Thread(gameService);
		gm.start();
		gm.interrupt();
	}

	@Test
	public void failStartGameTest() {
		LongId<User> userId = new LongId<User>(100500);
		LongId<GameSession> gameNullId = gameService.startGame(userId);
		assertNotNull(gameNullId);
		gameNullId = gameService.startGame(userId);
		assertNull(gameNullId);
	}

	@Test
	public void aStartGameTest() {
		gameId = gameService.startGame(userId);
		assertNotNull(gameId);
		assertTrue(messageSystem.execForAbonent(frontend));
	}

	@Test
	public void bJoinToGameTest() {
		assertTrue(gameService.joinToGame(victimId, gameId));
		int posValue = 100500;

		gameService.refreshPosition(victimId, posValue, posValue, posValue, posValue);
		Thread thr = new Thread(gameService);
		thr.start();

		TimeHelper.sleep(100);
		thr.interrupt();

		assertNotNull(gameService.startGame(victimId));
	}

	@Test
	public void cAddFireballTest() {
		gameService.addFireball(userId, new Fireball(0,0,1,1));
	}

	@Test
	public void updateAvalibleGameSessionTest() {
		Set<LongId<GameSession>> set = new HashSet<LongId<GameSession>>();
		set.add(new LongId<GameSession>(100));
		set.add(new LongId<GameSession>(500));
		MessageUpdateAvalibleGameSession msg = new MessageUpdateAvalibleGameSession(gameService.getAddress(), frontend.getAddress(), userId, set);

		assertTrue(msg.exec((Abonent)frontend));
	}

}
