package message;


import base.*;
import frontend.MessageGameStep;
import game.GameServiceImpl;
import game.GameSession;
import game.MessageStartGameToFrontend;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import resource.MapResource;
import user.User;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageToTest extends Assert {

	@Mock  private     Frontend       frontend;
    @Mock  private     ResourceSystem resourceSystem;
	       private     MessageSystem  messageSystem;
	       private     GameService    gameService;
		private LongId<User> userLongId = new LongId<User>(1);


    @Before
    public  void setUp() throws Exception {
        messageSystem = new MessageSystemImpl();
        when(resourceSystem.getResource(MapResource.class)).thenReturn(new MapResource(100,100,"Map1"));
        gameService = new GameServiceImpl(messageSystem, resourceSystem);
	}

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
	}

	@Test
	public void messageGameStep() {
		MessageGameStep messageGameStep = new MessageGameStep(null, gameService.getAddress(), userLongId);
		//messageGameStep.exec(gameService);

	}
}
