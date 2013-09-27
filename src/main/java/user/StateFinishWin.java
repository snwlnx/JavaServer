package user;

import base.Frontend;
import base.LongId;
import frontend.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 8/18/13
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateFinishWin implements UserState {

    public void processUserState(Frontend frontend, LongId<UserSession> sessionId,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer  = response.getWriter();
        UserSession userSession = frontend.getUserSessionBySessionId(sessionId);


        if(request.getParameter("game") != null) {
            writer.print("false");
            return;
        }
        String refresh = request.getParameter("refresh");
        if(refresh != null && refresh.equals("ok")){
            userSession.updateUserState(new StateAuthorized());
            writer.print( Page.AuthAccept(sessionId, userSession.getUserName(), userSession.getUserId()));
            return;
        }

        writer.print(Page.FinishWin(sessionId));
    }


}
