package user;

import base.Abonent;
import base.LongId;
import base.MessageSystem;
import base.Player;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
//	    Set<LongId<GameSession>> gamIdSet = new HashSet<LongId<GameSession>>();
//	    gamIdSet.add(new LongId<GameSession>(1));
//
//
//	    when(session.getAvailableGameSessions()).thenReturn(gamIdSet);
//	    when(frontend.getUserSession(userGameNumber)).thenReturn(session);

        //request
        when(request.getRequestURI()).thenReturn("request");
        when(request.getCookies()).thenReturn(new Cookie[0]);
        when(request.getParameter((anyString()))).thenReturn("0");
        when(request.getParameter("showChats")).thenReturn("yes");
        when(request.getParameter("Exit")).thenReturn("Exit");
        when(request.getMethod()).thenReturn("POST");//.thenReturn("POST");

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
    public void authorized() throws IOException {
	    StateAuthorized stateAuthorized = new StateAuthorized();
	    stateAuthorized.processUserState(frontend,userSessionNumber,request,response);

	    //System.out.println(stateAuthorized.getLinkForAvailableGames(frontend, userSessionNumber));
    }
    @Test
    public void finishLose() throws IOException {
	    StateFinishLose stateFinishLose = new StateFinishLose();
	    stateFinishLose.processUserState(frontend,userSessionNumber,request,response);

	    when(request.getParameter("game")).thenReturn(null);
	    stateFinishLose.processUserState(frontend,userSessionNumber,request,response);

	    when(request.getParameter("refresh")).thenReturn("fail");
	    stateFinishLose.processUserState(frontend,userSessionNumber,request,response);
    }
    @Test
    public void finishWin() throws IOException {
	    StateFinishWin stateFinishWin = new StateFinishWin();
	    stateFinishWin.processUserState(frontend,userSessionNumber,request,response);

	    when(request.getParameter("game")).thenReturn(null);
	    stateFinishWin.processUserState(frontend,userSessionNumber,request,response);

	    when(request.getParameter("refresh")).thenReturn("fail");
	    stateFinishWin.processUserState(frontend,userSessionNumber,request,response);

//	    when(request.getParameter("refresh")).thenReturn("ok");
	    //new StateFinishWin().processUserState(frontend,userSessionNumber,request,response);
    }

    @Test
    public void play() throws IOException {
	    StatePlay statePlay =  new StatePlay();
	    statePlay.processUserState(frontend, userSessionNumber, request, response);
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
