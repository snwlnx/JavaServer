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
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatePlay implements UserState{



    public  boolean chatExit(Frontend frontend, HttpServletRequest request, LongId<UserSession> sessionId) {
        String checkButton = request.getParameter("Exit");
        if (checkButton != null) {
            if (checkButton.equals("Exit")) {
                frontend.updateUserState(sessionId, new StateAuthorized());
                return true;
            }
        }
        return false;
    }


    @Override
    public void processUserState(Frontend frontend, LongId<UserSession> sessionId,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer      = response.getWriter();
        UserSession userSession = frontend.getUserSessionBySessionId(sessionId);

        if (request.getMethod().equals("POST") && request.getParameter("game") != null) {
            String action = request.getParameter("action");

            if (action != null) {
                writer.print( frontend.processAction(action,request, response, userSession));
            return;
            }
        }

        if (!chatExit(frontend,request, sessionId)) {

            String responseString  =  frontend.processGameStep(request,sessionId);

            if (responseString != null) {
                writer.print(responseString);
                return;
            }
            writer.print("");
            return;
        }
        writer.print(Page.AuthAccept(sessionId, userSession.getUserName(), userSession.getUserId()));


    }
}


