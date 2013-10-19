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

@RunWith(MockitoJUnitRunner.class)
public class StateTest {
    @Mock HttpServletRequest   servletRequest;
    @Mock HttpServletResponse  servletResponse;
    @Mock Request              baseRequest;
    @Mock UserSession          userSession;
    @Mock PrintWriter          printWriter;
    @Mock MessageSystem        messageSystem;
    FrontendImpl               frontend;

    LongId<User>         gameNumber =  new LongId<User>(1);
    LongId<UserSession>  sessionNumber =  new LongId<UserSession>(1);
    String               userSessionName   =  "user_name";
    Integer              userHealth        =  100;


    @Test
    public void authorized() throws IOException {
	    StateAuthorized stateAuthorized = new StateAuthorized();
	    stateAuthorized.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    Assert.assertEquals("[1]", stateAuthorized.getLinkForAvailableGames(frontend, sessionNumber));

	    frontend.getUserSession(gameNumber).setAvailableGameSessions(new HashSet<LongId<GameSession>>());
	    Assert.assertEquals("[]", stateAuthorized.getLinkForAvailableGames(frontend, sessionNumber));

	    Frontend frontend1 = mock(Frontend.class);
	    UserSession session1 = mock(UserSession.class);
	    when(session1.getAvailableGameSessions()).thenReturn(new HashSet<LongId<GameSession>>());
	    when(session1.getUserName()).thenReturn("ok");
	    when(session1.getUserId()).thenReturn(new LongId<User>(1));


//	    when(frontend1.getUserSessionBySessionId(sessionNumber)).thenReturn(session1);
//	    stateAuthorized.getLinkForAvailableGames(frontend1, sessionNumber);

//	    when(frontend.chatSelected((HttpServletRequest) any(), (LongId<UserSession>) any())).thenReturn(true);
//	    stateAuthorized.getLinkForAvailableGames(frontend1, sessionNumber);

    }
    @Test
    public void finishLose() throws IOException {
	    StateFinishLose stateFinishLose = new StateFinishLose();
	    stateFinishLose.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getParameter("game")).thenReturn(null);
	    stateFinishLose.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getParameter("refresh")).thenReturn("fail");
	    stateFinishLose.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getParameter("refresh")).thenReturn("ok");
	    stateFinishLose.processUserState(frontend, sessionNumber, servletRequest, servletResponse);
    }


    @Before
    public void setUp() throws Exception {
        //servletRequest
        when(servletRequest.getRequestURI()).thenReturn("servletRequest");
        when(servletRequest.getCookies()).thenReturn(new Cookie[0]);
        when(servletRequest.getParameter((anyString()))).thenReturn("0");
        when(servletRequest.getParameter("showChats")).thenReturn("yes");
        when(servletRequest.getParameter("Exit")).thenReturn("Exit");

        when(servletRequest.getMethod()).thenReturn("POST");

        when(servletRequest.getMethod()).thenReturn("POST");//.thenReturn("POST");


        //servletResponse
        when(servletResponse.getWriter()).thenReturn(printWriter);

        //messageSystem
        when(messageSystem.execForAbonent(frontend)).thenReturn(true);
        when(messageSystem.getAddressService()).thenReturn(null);
        when(messageSystem.getAddress((Class<?>) any())).thenReturn(null);
        when(messageSystem.execForAbonent((Abonent)any())).thenReturn(true);
        doNothing().when(messageSystem).sendMessage((Message)any());
        doNothing().when(messageSystem).addService((Class<?>)any(),(Abonent)any());

        frontend = new FrontendImpl(messageSystem);
        frontend.addUserSession(sessionNumber, userSessionName);
        frontend.addUserIdToSession(gameNumber, sessionNumber);
        frontend.getUserSession(gameNumber).setPlayer(new Player());

        Set<LongId<GameSession>> gamIdSet = new HashSet<LongId<GameSession>>();
        gamIdSet.add(new LongId<GameSession>(1));
        frontend.getUserSession(gameNumber).setAvailableGameSessions(gamIdSet);
        frontend.getUserSessionBySessionId(sessionNumber).setUserId(gameNumber);

    }


    @Test
    public void finishWin() throws IOException {
	    StateFinishWin stateFinishWin = new StateFinishWin();
	    stateFinishWin.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getParameter("game")).thenReturn(null);
	    stateFinishWin.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getParameter("refresh")).thenReturn("fail");
	    stateFinishWin.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    FrontendImpl frontend1 = mock(FrontendImpl.class);
	    UserSession session1 = mock(UserSession.class);
	    when(session1.getUserName()).thenReturn("ok");
	    when(session1.getUserId()).thenReturn(new LongId<User>(1));


	    when(frontend1.getUserSessionBySessionId(sessionNumber)).thenReturn(session1);
	    when(servletRequest.getParameter("refresh")).thenReturn("ok");
	    new StateFinishWin().processUserState(frontend, sessionNumber, servletRequest, servletResponse);
    }

    @Test
    public void play() throws IOException {
	    StatePlay statePlay =  new StatePlay();
	    statePlay.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getParameter("action")).thenReturn(null);
	    statePlay.processUserState(frontend, sessionNumber, servletRequest, servletResponse);

	    when(servletRequest.getMethod()).thenReturn("GET");
	    when(servletRequest.getParameter("Exit")).thenReturn(null);
	    FrontendImpl frontend1 = mock(FrontendImpl.class);
	    doNothing().doThrow(new RuntimeException()).when(frontend1).updateUserState(( LongId<UserSession> ) any(), ( UserState ) any());
	    when(frontend1.processGameStep(( HttpServletRequest ) any(), ( LongId<UserSession> ) any())).thenReturn(null);
	    UserSession session1 = new UserSession("name");
	    session1.setUserId(new LongId<User>(1));

	    when(frontend1.getUserSessionBySessionId(sessionNumber)).thenReturn(new UserSession("user_name"));
	    statePlay.processUserState(frontend1, sessionNumber, servletRequest, servletResponse);

	    when(frontend1.processGameStep(( HttpServletRequest ) any(), ( LongId<UserSession> ) any())).thenReturn("ok");
	    statePlay.processUserState(frontend1, sessionNumber, servletRequest, servletResponse);


	    when(servletRequest.getParameter("action")).thenReturn("f");
	    statePlay.processUserState(frontend1, sessionNumber, servletRequest, servletResponse);

//	    when(servletRequest.getParameter("Exit")).thenReturn("Exit");
//	    statePlay.processUserState(frontend1, sessionNumber, servletRequest, servletResponse);
    }
    @Test
    public void notAuth() throws IOException {
        new StateNotAuthorized().processUserState(frontend, sessionNumber, servletRequest, servletResponse);
    }
    @Test
    public void statеWait() throws IOException {
        new StateWaitForAuth().processUserState(frontend, sessionNumber, servletRequest, servletResponse);
    }

	@Test
	public void chatExit() throws IOException {
		boolean retVal;
		StatePlay state = new StatePlay();

		retVal = state.chatExit(frontend, servletRequest, sessionNumber);
		Assert.assertTrue(retVal);

		when(servletRequest.getParameter("Exit")).thenReturn(null);
		retVal = state.chatExit(frontend, servletRequest, sessionNumber);
		Assert.assertFalse(retVal);

		when(servletRequest.getParameter("Exit")).thenReturn("FAIL");
		retVal = state.chatExit(frontend, servletRequest, sessionNumber);
		Assert.assertFalse(retVal);
	}
}
