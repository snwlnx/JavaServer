package user;

import base.Abonent;
import base.LongId;
import base.MessageSystem;
import base.Player;
import frontend.FrontendImpl;
import message.Message;
import org.eclipse.jetty.server.Request;
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
        when(request.getMethod()).thenReturn("POST").thenReturn("POST");

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
    public void testStateAuthorized() throws IOException {
        new StateAuthorized().processUserState(frontend,userSessionNumber,request,response);
    }
    @Test
    public void testStatеFinishLose() throws IOException {
        new StateFinishWin().processUserState(frontend,userSessionNumber,request,response);
    }
    @Test
    public void testStatеFinishWin() throws IOException {
        new StateFinishWin().processUserState(frontend,userSessionNumber,request,response);
    }

    @Test
    public void testStatеPlay() throws IOException {
        new StatePlay().processUserState(frontend,userSessionNumber,request,response);
    }
    @Test
    public void testStatеNotAuth() throws IOException {
        new StateNotAuthorized().processUserState(frontend,userSessionNumber,request,response);
    }
    @Test
    public void testStatеWait() throws IOException {
        new StateWaitForAuth().processUserState(frontend,userSessionNumber,request,response);
    }

}
