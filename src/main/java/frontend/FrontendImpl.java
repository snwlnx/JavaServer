package frontend;


import base.*;
import game.ChatMessage;
import game.GameSession;
import message.Message;
import message.MessageToGameService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.JSONArray;
import user.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FrontendImpl extends AbstractHandler implements Frontend {

    public static Map<LongId<UserSession>, UserSession>  sessions
            = new ConcurrentHashMap<LongId<UserSession>, UserSession>();
    public static Map<LongId<User>, LongId<UserSession>> userIdToSessionId
            = new ConcurrentHashMap<LongId<User>, LongId<UserSession>>();

    private MessageSystem messageSystem;
    private Address       address;
    private long lastTime = System.currentTimeMillis();

    public void addUserSession(LongId<UserSession> sessionId, String userName) {
        UserSession userSession = new UserSession(userName);
        sessions.put(sessionId, userSession);
    }

    public void addUserIdToSession(LongId<User> userId, LongId<UserSession> sessionId) {
        userIdToSessionId.put(userId,sessionId);
    }


    public LongId<UserSession> getSessionIdFromCookie(HttpServletRequest request) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cook : cookies)
                if (cook.getName().equals("sessionId")) {
                    cookie = cook;
                    break;
                }
        return (cookie == null) ? null : new LongId<UserSession>(Long.parseLong(cookie.getValue()));

    }

    private boolean favIcon(HttpServletRequest request) {
        return  (request.getRequestURI().equals("/favIcon.ico"))?true:false;
    }


    private LongId<UserSession> createNewSessionId(HttpServletResponse response) throws IOException {
        Random rand = new Random();
        LongId<UserSession> sessionId;
        do {
            sessionId = new LongId<UserSession>(rand.nextLong());
        } while (sessions.containsKey(sessionId));
        Cookie cookie = new Cookie("sessionId", sessionId.toString());
        //cookie.setMaxAge(1000);
        response.addCookie(cookie);
        return sessionId;
    }


    private void createNewUserSessionAndSendMessageR(LongId<UserSession> sessionId,
                                                     String              userName,
                                                     HttpServletResponse response)
                                                                            throws IOException {
        UserSession userSession = new UserSession(userName);
        sessions.put(sessionId, userSession);

        Message message = new MessageUserIdGet(
                this.getAddress(),
                messageSystem.getAddress(DbService.class),
                sessionId,
                userName);
        messageSystem.sendMessage(message);

        response.getWriter().print(Page.WaitForAuthorization(sessionId, userName));
    }

    private void joinToSelectedChat(LongId<UserSession> sessionId, LongId<GameSession> gameSessionId) {
        LongId<User> userId = sessions.get(sessionId).getUserId();
        MessageToGameService message = new MessageJoinToGame(
                address,
                messageSystem.getAddress(GameService.class),
                userId,
                gameSessionId);

        messageSystem.sendMessage(message);
    }

    public boolean chatSelected(HttpServletRequest request, LongId<UserSession> sessionId) {
        String checkButton = request.getParameter("New");

        if (checkButton != null && checkButton.equals("New")) {
            updateUserState(sessionId,new StatePlay());
            startGameSM(sessionId);
            return true;
        }

        checkButton = request.getParameter("selectedChat");
        if (checkButton != null) {
            updateUserState(sessionId, new StatePlay());
            joinToSelectedChat(sessionId, new LongId<GameSession>(Integer.parseInt(checkButton)));
            // hardCode = true;
            return true;
        }

        return false;
    }

/*  deprecated
    private boolean chatExit(HttpServletRequest request, LongId<UserSession> sessionId) {
        String checkButton = request.getParameter("Exit");
        if (checkButton != null) {
            if (checkButton.equals("Exit")) {
                updateUserState(sessionId,new StateAuthorized());
                return true;
            }
        }
        return false;
    }*/


    private void addMessageToGame(String chatMessage, LongId<UserSession> sessionId) {
        LongId<User> userId = sessions.get(sessionId).getUserId();

        MessageToGameService message = new MessageGameAction(
                this.getAddress(),
                messageSystem.getAddress(GameService.class),
                userId,
                chatMessage);
        messageSystem.sendMessage(message);
    }

    private void messageAdd(HttpServletRequest request, LongId<UserSession> sessionId) {

        String chatMessage = request.getParameter("message");
        if (chatMessage == null || chatMessage.equals(""))
            return;
        addMessageToGame(chatMessage, sessionId);

    }


    public void startGameSM(LongId<UserSession> sessionId) {

        LongId<User> userId = sessions.get(sessionId).getUserId();
        MessageToGameService message = new MessageStartGame(
                this.getAddress(),
                messageSystem.getAddress(GameService.class), userId);

        messageSystem.sendMessage(message);

    }

    private boolean existAvailableGames(LongId<UserSession> sessionId) {
        Set<LongId<GameSession>> gamesId = sessions.get(sessionId).getAvailableGameSessions();

        return (gamesId == null)?false:((gamesId.size() !=0 )?true:false);
/*        if (gamesId != null)
            if (gamesId.size() != 0)
                return true;
        return false;*/

    }

    public boolean availableGames(HttpServletRequest request, LongId<UserSession> sessionId) {
        LongId<User> userId = sessions.get(sessionId).getUserId();
        Message message = new MessageGetAvailableGameSession(
                this.getAddress(),
                messageSystem.getAddress(GameService.class),
                userId);
        messageSystem.sendMessage(message);
        String key = request.getParameter("showChats");

        return (key == null)?false:((key.equals("yes") && existAvailableGames(sessionId))?true:false);
/*        if (key == null)
            return false;
        if (key.equals("yes")) {
            if (existAvailableGames(sessionId))
                return true;
        }
        return false;*/
    }

/*
    private String getLinkForAvailableGames(LongId<UserSession> sessionId) {

        JSONArray jsonArray = new JSONArray();
        Set<LongId<GameSession>> gamesId = sessions.get(sessionId).getAvailableGameSessions();
        Iterator<LongId<GameSession>> iter = gamesId.iterator();
        while (iter.hasNext())
            jsonArray.add(iter.next().get());
        return jsonArray.toString();
    }
*/

    public void removeSession(LongId<UserSession> sessionId){
        if(sessionId != null){
            sessions.remove(sessionId);
        }
    }

    public String processAction(String              action,
                                HttpServletRequest  request,
                                HttpServletResponse response,
                                UserSession         userSession){
        if (action != null) {
            if (action.equals("position")) {
                positionFromClient(request, userSession);
            } else if (action.equals("fireball")) {
                fireballFromClient(request, userSession);
            } else if (action.equals("get")) {
                return infoFromClient(response, userSession);
            }
        }
        return "";

    }

    public String processGameStep(HttpServletRequest request, LongId<UserSession> sessionId){
        messageAdd(request, sessionId);
        updateGameStep(sessionId);
        return responseNewMessage(sessionId);

    }

    private void fireballFromClient(HttpServletRequest request, UserSession session) {

        float   x  = Float.parseFloat(request.getParameter("positionX")),
                y  = Float.parseFloat(request.getParameter("positionY")),
                vX = Float.parseFloat(request.getParameter("velocityX")),
                vY = Float.parseFloat(request.getParameter("velocityY"));

        Fireball fb = new Fireball(x, y, vX, vY);

        Message message = new MessageAddFireball(
                this.getAddress(),
                messageSystem.getAddress(GameService.class),
                session.getUserId(),
                fb);
        messageSystem.sendMessage(message);
    }

    private void positionFromClient(HttpServletRequest request, UserSession session) {
        int     x  = Integer.parseInt(request.getParameter("positionX")),
                y  = Integer.parseInt(request.getParameter("positionY")),
                vX = Integer.parseInt(request.getParameter("velocityX")),
                vY = Integer.parseInt(request.getParameter("velocityY"));

        Player pl = session.getPlayer();
        pl.setPosition(x, y);
        pl.setVelocity(vX, vY);
        Message message = new MessageRefreshPosition(
                address,
                messageSystem.getAddress(GameService.class),
                session.getUserId(),
                x, y, vX, vY);
        messageSystem.sendMessage(message);
    }

    private String infoFromClient(HttpServletResponse response, UserSession user) {
        response.setContentType("application/json");

        JSONArray responseJson = new JSONArray(),
                  balls        = new JSONArray(),
                  enems        = new JSONArray();

        balls.addAll(user.getFireballs());
        enems.addAll(user.getEnemies());
        responseJson.add(balls);
        responseJson.add(enems);
        responseJson.add(user.getPlayer());
        //responseJson.add(true);

        return responseJson.toString();
    }

    public void updateFireballs(LongId<User> userId, LinkedList<Fireball> fireballs) {
        UserSession session = getUserSession(userId);
        if (session != null) {
            session.setFireballs(fireballs);
        }
    }

    public void updateHealth(LongId<User> userId, int health) {
        UserSession session = getUserSession(userId);
        if(session != null) {
            session.getPlayer().setHealth(health);
        }
    }


    private String responseNewMessage(LongId<UserSession> sessionId) {
        ChatMessage[] newChatMessage = sessions.get(sessionId).getNewMessage();

        if (newChatMessage != null) {
            //responseString = Page.PlayWithNewMessages(sessionId, userSession.getUserName(),newChatMessage);
            String responseString = "";
            for (int i = 0; i < newChatMessage.length; i++) {
                responseString += "<br>" + newChatMessage[i].getStringMessage();
            }

            return responseString;
        }
        return null;
    }

    private void setResponseSettings(Request baseRequest, HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
    }

    public void handle(String               target,
                       Request              baseRequest,
                       HttpServletRequest   request,
                       HttpServletResponse  response)
            throws IOException, ServletException {

        if (favIcon(request))
            return;
        setResponseSettings(baseRequest, response);

        LongId<UserSession> sessionId = getSessionIdFromCookie(request);
        if (sessionId == null) {
            sessionId = createNewSessionId(response);
        }

        UserSession userSession = sessions.get(sessionId);

        if (userSession == null) {
            String userName;
            userName = request.getParameter("user_name");
            if (userName == null) {
                response.getWriter().print(Page.Start(sessionId));
                return;
            }
            createNewUserSessionAndSendMessageR(sessionId, userName, response);
            return;
        }
        userSession.updateLastTime();
        if(userSession.hasState()){
            userSession.processUserState(this,sessionId,request,response);
        }else {
          response.getWriter().print(Page.Start(sessionId));
        }
    }

    public FrontendImpl(MessageSystem msgSystem) {
        this.messageSystem = msgSystem;
        address = new Address();
        msgSystem.addService(Frontend.class, this);
    }

    private void updateGameStep(LongId<UserSession> sessionId) {
        LongId<User> userId = sessions.get(sessionId).getUserId();
        MessageToGameService message = new MessageGameStep(
                this.getAddress(),
                messageSystem.getAddress(GameService.class), userId);
        messageSystem.sendMessage(message);
    }

    public void run() {
        while (true) {
            //clearSessionsByTimeout();
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(17);
        }
    }

/*    private void clearSessionsByTimeout() {
        // todo убрать константы из кода

        long curTime = System.currentTimeMillis();
        if ((lastTime + 10000) < curTime) {
            lastTime = curTime;

            Iterator iter = sessions.keySet().iterator();
            LongId<UserSession> key;
            while (iter.hasNext()) {
                key = (LongId<UserSession>) (iter.next());
                if (sessions.get(key).getLastTime() + 20 * 1000 < curTime) {
                    // todo отправить сообщение игровой механике о том, что он больше не играет

                    System.out.println("Remove session " + key);
                    sessions.remove(key);
                }
            }
        }
    }*/

    public void updateUserId(LongId<UserSession> sessionId, LongId<User> userId) {
        UserSession session = sessions.get(sessionId);
        if (session == null) {
            return;
        }
        if (userId == null) {
            session.updateUserState(new StateNotAuthorized());
            return;
        }
        userIdToSessionId.put(userId, sessionId);
        session.setUserId(userId);
        session.updateUserState( new StateAuthorized());
    }

    public void updateUserState(LongId<UserSession> sessionId, UserState userState) {
        UserSession session = sessions.get(sessionId);
        if (session == null) {
            return;
        }
        session.updateUserState(userState);
    }

    public void startGame(LongId<User> userIdToGameSession, LongId<GameSession> gameSessionId) {
        LongId<UserSession> sessionId = userIdToSessionId.get(userIdToGameSession);
        sessions.get(sessionId).updateUserState(new StatePlay());
    }


    public void joinToGame(LongId<User> userIdToGameSession) {
        //TODO добавить юзера к чату
        //System.out.println(" join userIdToGameSession" + userIdToGameSession);
    }

    public void updateGameStep(LongId<User> userId, ChatMessage[] lastMessages) {
        LongId<UserSession> sessionId = userIdToSessionId.get(userId);
        sessions.get(sessionId).setNewMessages(lastMessages);
    }


    public void updateAvailableGameSessionForUser(LongId<User> userIdToGameSession,
                                                  Set<LongId<GameSession>> availableGameSessionsId) {
        sessions.
                get(userIdToSessionId.get(userIdToGameSession)).
                setAvailableGameSessions(availableGameSessionsId);
    }


    public void finishGame(LongId<User> userId, boolean win) {
        UserSession session = getUserSession(userId);
        if(session != null) {
            // todo вообще надо бы занулять!!!
            session.setEnemies(new LinkedList<Player>());
            session.setFireballs(new LinkedList<Fireball>());
            session.updateUserState(win ?  new StateFinishWin() : new StateFinishLose());
        }
    }

    public void refreshPosition(LongId<User> userId, LinkedList<Player> players) {
        UserSession session = getUserSession(userId);
        if (session != null) {
            session.setEnemies(players);
        }
    }

    public UserSession getUserSessionBySessionId(LongId<UserSession> sessionId){
        return sessions.get(sessionId);
    }

    public UserSession getUserSession(LongId<User> userId) {
        LongId<UserSession> sessionId = userIdToSessionId.get(userId);
/*        if (sessionId != null) {
            return sessions.get(sessionId);
        }
        return null;*/
        return (sessionId != null)?sessions.get(sessionId):null;
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
