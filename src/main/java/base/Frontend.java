package base;

import game.ChatMessage;
import game.GameSession;
import user.User;
import user.UserSession;
import user.UserState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.Set;

public interface Frontend extends Abonent,Runnable {

    public UserSession getUserSessionBySessionId(LongId<UserSession> sessionID);

    public boolean availableGames  (HttpServletRequest request,LongId<UserSession> sessionId);
    public boolean chatSelected    (HttpServletRequest request,LongId<UserSession> sessionId);
    public String  processGameStep (HttpServletRequest request, LongId<UserSession> sessionId);
    public String  processAction   (String action, HttpServletRequest request,
                                    HttpServletResponse response,UserSession userSession);


    public void removeSession                    (LongId<UserSession>      sessionId);
    public void updateUserId                     (LongId<UserSession>      sessionId,
                                                  LongId<User>             userId);
    public void updateGameStep                   (LongId<User>             userId,ChatMessage[] lastMessages);
    public void joinToGame                       (LongId<User>             userToGameSession);
    public void startGame                        (LongId<User>             userToGameSession,
                                                  LongId<GameSession>      gameSessionId );
    public void updateAvailableGameSessionForUser(LongId<User>             userToGameSession,
                                                  Set<LongId<GameSession>> availableGameSessionsId);
    public void finishGame                       (LongId<User>             userToGameSession, boolean win);

    public void refreshPosition (LongId<User> userId, LinkedList<Player>   players);
    public void updateFireballs (LongId<User> userId, LinkedList<Fireball> fireballs);
    public void updateHealth    (LongId<User> userId, int health);
    public void updateUserState (LongId<UserSession>  sessionId, UserState userState);
}
