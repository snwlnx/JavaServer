package user;

import base.Frontend;
import base.LongId;
import base.Player;
import frontend.Page;
import game.GameSession;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 8/18/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateAuthorized implements UserState {

    private String getLinkForAvailableGames(Frontend frontend,LongId<UserSession> sessionId) {

        JSONArray jsonArray = new JSONArray();
        Set<LongId<GameSession>> gamesId = frontend.getUserSessionBySessionId(sessionId).getAvailableGameSessions();
        Iterator<LongId<GameSession>> iter = gamesId.iterator();
        while (iter.hasNext())
            jsonArray.add(iter.next().get());
        return jsonArray.toString();
    }



    public void processUserState( Frontend frontend, LongId<UserSession> sessionId,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer      = response.getWriter();
        UserSession userSession = frontend.getUserSessionBySessionId(sessionId);

        if (frontend.availableGames(request, sessionId)) {
            writer.print(getLinkForAvailableGames(frontend,sessionId));
            return;
        }
        if (frontend.chatSelected(request, sessionId)) {
            Player pl = new Player((new Random()).nextInt(), 0);
            userSession.setPlayer(pl);
            writer.print(Page.PlayJQuery(sessionId, userSession.getUserName()));
            return;
        }
        writer.print(Page.AuthAccept(sessionId, userSession.getUserName(), userSession.getUserId()));

    }

}
