package message;


import base.Frontend;
import base.GameService;
import base.MessageSystem;
import org.junit.Assert;

public class MessageToTest extends Assert {

	private static       Frontend frontend;
	private static       MessageSystem messageSystem;
	private static       GameService gameService;
/*
	@BeforeClass
	public static void beforeClass() {
		messageSystem = new MessageSystemImpl();
		frontend = new FrontendMock(messageSystem);
		gameService = new GameServiceImpl(messageSystem, new ResourceSystemMock());

	}*/
/*
	@Test
	public void getFromTest() {
		MessageStartGameToFrontend msg = new MessageStartGameToFrontend(frontend.getAddress(), null, new LongId<User>(111), new LongId<GameSession>(11111));
		assertTrue(frontend.getAddress() == msg.getFrom());
	}

	@Test
	public void toFrontendTest() {
		MessageStartGameToFrontend msg = new MessageStartGameToFrontend(null, null, new LongId<User>(111), new LongId<GameSession>(11111));
		assertTrue(msg.exec((Abonent)frontend));
		assertFalse(msg.exec((Abonent)gameService));
	}*/
}
