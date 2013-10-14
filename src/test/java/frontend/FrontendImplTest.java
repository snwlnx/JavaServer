package frontend;

import base.*;
import game.ChatMessage;
import message.Message;
import org.eclipse.jetty.server.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import user.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/2/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class FrontendImplTest extends Assert {

    FrontendImpl         frontend;

    @Mock HttpServletRequest   request;
    @Mock HttpServletResponse  response;
    @Mock Request              baseRequest;
    @Mock PrintWriter          printWriter;
    @Mock MessageSystem        msgSystem;

    LongId<User>         userGameNumber    =  new LongId<User>(1);
    LongId<UserSession>  userSessionNumber =  new LongId<UserSession>(1);
    String               userSessionName   =  "user_name";
    Integer              userHealth        =  100;

    @Before
    public void setUp() throws Exception {
        //request
        when(request.getRequestURI()).thenReturn("request");
        when(request.getCookies()).thenReturn(new Cookie[0]);
        when(request.getParameter((anyString()))).thenReturn("0");

        //response
        when(response.getWriter()).thenReturn(printWriter);
        //msgSystem
        when(msgSystem.execForAbonent(frontend)).thenReturn(true);
        when(msgSystem.getAddressService()).thenReturn(null);
        when(msgSystem.getAddress((Class<?>) any())).thenReturn(null);
        when(msgSystem.execForAbonent((Abonent)any())).thenReturn(true);
        doNothing().when(msgSystem).sendMessage((Message)any());
        doNothing().when(msgSystem).addService((Class<?>)any(),(Abonent)any());

        frontend = new FrontendImpl(msgSystem);
        frontend.addUserSession(userSessionNumber, userSessionName);
        frontend.addUserIdToSession(userGameNumber, userSessionNumber);
        frontend.getUserSession(userGameNumber).setPlayer(new Player());
    }

    @Test
    public void testStartGame() {
        frontend.startGame(userSessionNumber);
    }

    @Test
    public void testUpdateUserState() {
        frontend.updateUserState(userSessionNumber,new StatePlay());
        UserState currentState = frontend.getUserSession(userGameNumber).getUserState();
        assertTrue(currentState instanceof StatePlay);
    }

    @Test
    public void testUpdateGameStep() {
        frontend.updateGameStep(userGameNumber, new ChatMessage[]  {new ChatMessage(userGameNumber,"some_text")});
        ChatMessage[] currMessages = frontend.getUserSession(userGameNumber).getNewMessage();
        assertNotNull(currMessages[0]);
    }

    @Test
    public void testFinishGame () {
        frontend.finishGame(userGameNumber,new Boolean(true));
        UserState currentState = frontend.getUserSession(userGameNumber).getUserState();
        assertTrue(currentState instanceof StateFinishWin);
    }

    @Test
    public void  testUpdateUserId () {
        frontend.updateUserId(userSessionNumber,userGameNumber);
        UserState currentState = frontend.getUserSession(userGameNumber).getUserState();
        assertTrue(currentState instanceof StateAuthorized);
    }

    @Test
    public void testUpdateHealth () {
          frontend.updateHealth(userGameNumber,userHealth);
          assertTrue(frontend.getUserSession(userGameNumber).getPlayer().getHealthMax() == userHealth);
    }

    @Test
    public void testUpdateFireBalls () {
        Fireball fireball = new Fireball(1,1,1,1);
        LinkedList<Fireball> listFireballs = new LinkedList<Fireball>();
        listFireballs.push(fireball);
        frontend.updateFireballs(userGameNumber, listFireballs);
        assertTrue(frontend.getUserSession(userGameNumber).getFireballs().contains(fireball));
    }



    @Test
    public void testProcessAction(){
        assertNotNull(frontend.processAction("position",request,response,frontend.getUserSession(userGameNumber)));
    }

    @Test
    public void testHandle() throws IOException, ServletException {
        frontend.handle("target",baseRequest,request,response);
    }
}
