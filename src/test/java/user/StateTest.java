package user;

import base.*;
import frontend.FrontendImpl;
import game.GameSession;
import message.Message;
import org.eclipse.jetty.server.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/15/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class StateTest {


    FrontendImpl               frontend;
    @Mock HttpServletRequest   request;
    @Mock HttpServletResponse  response;
    @Mock Request              baseRequest;
    @Mock PrintWriter          printWriter;
    @Mock MessageSystem        msgSystem;
	@Mock UserSession           session;

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
        when(request.getParameter("showChats")).thenReturn("yes");
        when(request.getParameter("Exit")).thenReturn("Exit");
<<<<<<< HEAD
        when(request.getMethod()).thenReturn("POST");
=======
        when(request.getMethod()).thenReturn("POST");//.thenReturn("POST");
>>>>>>> a940dd2a73da0c9564b178555d7c4951a5fca2f5

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

	    Set<LongId<GameSession>> gamIdSet = new HashSet<LongId<GameSession>>();
	    gamIdSet.add(new LongId<GameSession>(1));
	    frontend.getUserSession(userGameNumber).setAvailableGameSessions(gamIdSet);
	    frontend.getUserSessionBySessionId(userSessionNumber).setUserId(userGameNumber);

    }


    @Test
    public void authorized() throws IOException {
	    StateAuthorized stateAuthorized = new StateAuthorized();
	    stateAuthorized.processUserState(frontend,userSessionNumber,request,response);

	    Assert.assertEquals("[1]", stateAuthorized.getLinkForAvailableGames(frontend, userSessionNumber));

	    frontend.getUserSession(userGameNumber).setAvailableGameSessions(new HashSet<LongId<GameSession>>());
	    Assert.assertEquals("[]", stateAuthorized.getLinkForAvailableGames(frontend, userSessionNumber));

	    Frontend frontend1 = mock(Frontend.class);
	    UserSession session1 = mock(UserSession.class);
	    when(session1.getAvailableGameSessions()).thenReturn(new HashSet<LongId<GameSession>>());
	    when(session1.getUserName()).thenReturn("ok");
	    when(session1.getUserId()).thenReturn(new LongId<User>(1));


//	    when(frontend1.getUserSessionBySessionId(userSessionNumber)).thenReturn(session1);
//	    stateAuthorized.getLinkForAvailableGames(frontend1, userSessionNumber);

//	    when(frontend.chatSelected((HttpServletRequest) any(), (LongId<UserSession>) any())).thenReturn(true);
//	    stateAuthorized.getLinkForAvailableGames(frontend1, userSessionNumber);

    }
    @Test
    public void finishLose() throws IOException {
	    StateFinishLose stateFinishLose = new StateFinishLose();
	    stateFinishLose.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getParameter("game")).thenReturn(null);
	    stateFinishLose.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getParameter("refresh")).thenReturn("fail");
	    stateFinishLose.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getParameter("refresh")).thenReturn("ok");
	    stateFinishLose.processUserState(frontend, userSessionNumber, request, response);
    }
    @Test
    public void finishWin() throws IOException {
	    StateFinishWin stateFinishWin = new StateFinishWin();
	    stateFinishWin.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getParameter("game")).thenReturn(null);
	    stateFinishWin.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getParameter("refresh")).thenReturn("fail");
	    stateFinishWin.processUserState(frontend, userSessionNumber, request, response);

	    FrontendImpl frontend1 = mock(FrontendImpl.class);
	    UserSession session1 = mock(UserSession.class);
	    when(session1.getUserName()).thenReturn("ok");
	    when(session1.getUserId()).thenReturn(new LongId<User>(1));


	    when(frontend1.getUserSessionBySessionId(userSessionNumber)).thenReturn(session1);
	    when(request.getParameter("refresh")).thenReturn("ok");
	    new StateFinishWin().processUserState(frontend,userSessionNumber,request,response);
    }

    @Test
    public void play() throws IOException {
	    StatePlay statePlay =  new StatePlay();
	    statePlay.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getParameter("action")).thenReturn(null);
	    statePlay.processUserState(frontend, userSessionNumber, request, response);

	    when(request.getMethod()).thenReturn("GET");
	    when(request.getParameter("Exit")).thenReturn(null);
	    FrontendImpl frontend1 = mock(FrontendImpl.class);
	    doNothing().doThrow(new RuntimeException()).when(frontend1).updateUserState((LongId<UserSession>) any(), (UserState) any());
	    when(frontend1.processGameStep((HttpServletRequest) any(), (LongId<UserSession>) any())).thenReturn(null);
	    UserSession session1 = new UserSession("name");
	    session1.setUserId(new LongId<User>(1));

	    when(frontend1.getUserSessionBySessionId(userSessionNumber)).thenReturn(new UserSession("user_name"));
	    statePlay.processUserState(frontend1, userSessionNumber, request, response);

	    when(frontend1.processGameStep((HttpServletRequest) any(),  (LongId<UserSession>) any())).thenReturn("ok");
	    statePlay.processUserState(frontend1, userSessionNumber, request, response);


	    when(request.getParameter("action")).thenReturn("f");
	    statePlay.processUserState(frontend1, userSessionNumber, request, response);

//	    when(request.getParameter("Exit")).thenReturn("Exit");
//	    statePlay.processUserState(frontend1, userSessionNumber, request, response);
    }
    @Test
    public void notAuth() throws IOException {
        new StateNotAuthorized().processUserState(frontend,userSessionNumber,request,response);
    }
    @Test
    public void statеWait() throws IOException {
        new StateWaitForAuth().processUserState(frontend,userSessionNumber,request,response);
    }

	@Test
	public void chatExit() throws IOException {
		boolean retVal;
		StatePlay state = new StatePlay();

		retVal = state.chatExit(frontend, request, userSessionNumber);
		Assert.assertTrue(retVal);

		when(request.getParameter("Exit")).thenReturn(null);
		retVal = state.chatExit(frontend, request, userSessionNumber);
		Assert.assertFalse(retVal);

		when(request.getParameter("Exit")).thenReturn("FAIL");
		retVal = state.chatExit(frontend, request, userSessionNumber);
		Assert.assertFalse(retVal);
	}
}
