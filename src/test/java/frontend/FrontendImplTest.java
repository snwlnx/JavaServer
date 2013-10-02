package frontend;

import base.LongId;
import game.ChatMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.StatePlay;
import user.User;
import user.UserSession;
import user.UserState;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/2/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrontendImplTest extends Assert {

    FrontendImpl frontend;

    LongId<User>         user_game_number    =  new LongId<User>(1);
    LongId<UserSession>  user_session_number =  new LongId<UserSession>(1);
    String               user_session_name   =  "user_name";


    @Before
    public void setUp() throws Exception {
        frontend = new FrontendImpl(new MessageSystemMock());
        frontend.addUserSession(user_session_number,user_session_name);
        frontend.addUserIdToSession(user_game_number,user_session_number);
    }

    @Test
    public void testStartGame() {
        frontend.startGame(user_session_number);
    }

    @Test
    public void testUpdateUserState() {
        frontend.updateUserState(user_session_number,new StatePlay());
        UserState currentState = frontend.getUserSession(user_game_number).getUserState();
        assertTrue(currentState instanceof StatePlay);
    }


    @Test
    public void testUpdateGameStep() {
        frontend.updateGameStep(user_game_number, new ChatMessage[]  {new ChatMessage(user_game_number,"some_text")});
        ChatMessage[] currMessages = frontend.getUserSession(user_game_number).getNewMessage();
        assertNotNull(currMessages[0]);

    }

}
