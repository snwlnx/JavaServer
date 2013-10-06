package frontend;

import base.Fireball;
import base.LongId;
import game.ChatMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/2/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrontendImplTest extends Assert {

    FrontendImpl         frontend;
    HttpServletRequest   request;
    LongId<User>         user_game_number    =  new LongId<User>(1);
    LongId<UserSession>  user_session_number =  new LongId<UserSession>(1);
    String               user_session_name   =  "user_name";
    Integer              user_health         =  100;

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

    @Test
    public void testFinishGame () {
//        frontend.finishGame(user_game_number,new Boolean(true));
//        UserState currentState = frontend.getUserSession(user_game_number).getUserState();
//        assertTrue(currentState instanceof StateFinishWin);
    }

    @Test
    public void  testUpdateUserId () {
//        frontend.updateUserId(user_session_number,user_game_number);
//        UserState currentState = frontend.getUserSession(user_game_number).getUserState();
//        assertTrue(currentState instanceof StateAuthorized);
    }

    @Test
    public void testUpdateHealth () {
//        frontend.updateHealth(user_game_number,user_health);
//        assertTrue(frontend.getUserSession(user_game_number).getPlayer().getHealthMax() == user_health);
    }

    @Test
    public void testUpdateFireBalls () {
//        Fireball fireball = new Fireball(1,1,1,1);
//        LinkedList<Fireball> listFireballs = new LinkedList<Fireball>();
//        listFireballs.push(fireball);
//        frontend.updateFireballs(user_game_number, listFireballs);
//        assertTrue(frontend.getUserSession(user_game_number).getFireballs().contains(listFireballs));
    }
}
