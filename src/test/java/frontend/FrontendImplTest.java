package frontend;

import base.*;
import game.ChatMessage;
import game.GameSession;
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

    FrontendImpl               frontend;

    @Mock HttpServletRequest   request;
    @Mock HttpServletResponse  response;
    @Mock Request              baseRequest;
    @Mock PrintWriter          printWriter;
    @Mock MessageSystem        msgSystem;

    LongId<User>               userGameNumber        =  new LongId<User>(1);
    LongId<UserSession>        userSessionNumber     =  new LongId<UserSession>(1);
    LongId<GameSession>        userGameSessionNumber =  new LongId<GameSession>(1);
    String                     userSessionName       =  "user_name";
    Integer                    userHealth            =  100;

    @Before
    public void setUp() throws Exception {
        //request
        when(request.getRequestURI()).thenReturn("request");
        when(request.getCookies()).thenReturn(new Cookie[0]);
        when(request.getParameter((anyString()))).thenReturn("0");
        when(request.getParameter("showChats")).thenReturn("yes");
        when(request.getParameter("New")).thenReturn("New");
        when(request.getParameter("message")).thenReturn("message");

        //response
        when(response.getWriter()).thenReturn(printWriter);
        //msgSystem
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
    public void startGameSM() {
        frontend.startGameSM(userSessionNumber);
    }

    @Test
    public void updateUserState() {
        frontend.updateUserState(userSessionNumber,new StatePlay());
        UserState currentState = frontend.getUserSession(userGameNumber).getUserState();
        assertTrue(currentState instanceof StatePlay);
    }

    @Test
    public void updateGameStep() {
        frontend.updateGameStep(userGameNumber, new ChatMessage[]  {new ChatMessage(userGameNumber,"some_text")});
        ChatMessage[] currMessages = frontend.getUserSession(userGameNumber).getNewMessage();
        assertNotNull(currMessages[0]);
    }

    @Test
    public void finishGame () {
        frontend.finishGame(userGameNumber,new Boolean(true));
        UserState currentState = frontend.getUserSession(userGameNumber).getUserState();
        assertTrue(currentState instanceof StateFinishWin);
    }

    @Test
    public void  updateUserId () {
        frontend.updateUserId(userSessionNumber,userGameNumber);
        UserState currentState = frontend.getUserSession(userGameNumber).getUserState();
        assertTrue(currentState instanceof StateAuthorized);
    }

    @Test
    public void updateHealth () {
          frontend.updateHealth(userGameNumber,userHealth);
          assertTrue(frontend.getUserSession(userGameNumber).getPlayer().getHealthMax() == userHealth);
    }

    @Test
    public void updateFireBalls () {
        Fireball fireball = new Fireball(1,1,1,1);
        LinkedList<Fireball> listFireballs = new LinkedList<Fireball>();
        listFireballs.push(fireball);
        frontend.updateFireballs(userGameNumber, listFireballs);
        assertTrue(frontend.getUserSession(userGameNumber).getFireballs().contains(fireball));
    }

    @Test
    public void processAction(){
        assertNotNull(frontend.processAction("position",request,response,frontend.getUserSession(userGameNumber)));
        assertNotNull(frontend.processAction("fireball",request,response,frontend.getUserSession(userGameNumber)));
        assertNotNull(frontend.processAction("get",request,response,frontend.getUserSession(userGameNumber)));
    }

    @Test
    public void handle() throws IOException, ServletException {
        frontend.handle("target",baseRequest,request,response);
    }

    @Test
    public void availableGames(){
        assertFalse(frontend.availableGames(request,userSessionNumber));
    }
    @Test
    public void removeSession(){
        frontend.removeSession(userSessionNumber);
    }

    @Test
    public void selectChat(){
        assertTrue(frontend.chatSelected(request, userSessionNumber));
    }

    @Test
    public void processGameStep(){
        assertNull(frontend.processGameStep(request,userSessionNumber));
    }

    @Test
    public void getMessageSystem(){
        assertTrue(frontend.getMessageSystem() instanceof MessageSystem);
    }

    @Test
    public void refreshPosition(){
        frontend.refreshPosition(userGameNumber,null);
        assertNull(frontend.getUserSession(userGameNumber).getEnemies());
    }

    @Test
    public void startGame(){
        frontend.startGame(userGameNumber,userGameSessionNumber);
        assertTrue(frontend.getUserSession(userGameNumber).getUserState() instanceof StatePlay);

    }

}
