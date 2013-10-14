package game;

import base.*;
import message.MessageSystemImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import resource.MapResource;
import user.User;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

		frontend =  mock(Frontend.class);
        when(frontend.getAddress()).thenReturn(new Address());

        messageSystem.addService(Frontend.class,frontend);
		//frontend = new FrontendMock(messageSystem);
		userId   = new LongId<User>(111);
		victimId = new LongId<User>(999);

        ResourceSystem resourceSystem = mock(ResourceSystem.class);
        when(resourceSystem.getResource(MapResource.class)).thenReturn(new MapResource(100,100,"Map1"));
        doNothing().when(resourceSystem).globalInit();

        gameService = new GameServiceImpl(messageSystem, resourceSystem);
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
