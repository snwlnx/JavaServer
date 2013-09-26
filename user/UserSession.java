package user;

import base.Fireball;
import base.Frontend;
import base.LongId;
import base.Player;
import game.ChatMessage;
import game.GameSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

public class UserSession {

    private String        name;
    private UserState     userState;
    private long          lastTime;
    private ChatMessage[] newMessages;
    private LongId<User>  userId;
    private Set<LongId<GameSession>> availableGameSessionsId;

    // Игровые переменные
    private Player               player;
    private LinkedList<Player>   enemies;
    private LinkedList<Fireball> fireballs;

    public UserSession(String name) {
        availableGameSessionsId = null;
        userId = null;
        this.name = name;
        this.userState = null;
        this.updateLastTime();
        this.enemies = new LinkedList<Player>();
        this.fireballs =  new LinkedList<Fireball>();
    }

    public LongId<User> getUserId() {
        return userId;
    }

    public String getUserName() {
        return name;
    }

    public void setUserId(LongId<User> id) {
        userId = id;
    }

    public void updateUserState(UserState userState) {
        this.userState = userState;
    }

    public UserState getUserState() {
        return userState;
    }

    public void updateLastTime() {
        lastTime = System.currentTimeMillis();
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setNewMessages(ChatMessage[] newMessages) {
        this.newMessages = newMessages;

    }

    public void setAvailableGameSessions(Set<LongId<GameSession>> availableGameSessionsId) {
        this.availableGameSessionsId = availableGameSessionsId;
    }

    public Set<LongId<GameSession>> getAvailableGameSessions() {
        return availableGameSessionsId;
    }

    public ChatMessage[] getNewMessage() {

        ChatMessage[] newMessages = this.newMessages;
        this.newMessages = null;
        return newMessages;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player pl) {
        player = pl;
    }

    public LinkedList<Player> getEnemies() {
        return enemies;
    }

    public void setEnemies(LinkedList<Player> pls) {
        enemies = pls;
    }

    public void setFireballs(LinkedList<Fireball> fireballs) {
        this.fireballs = fireballs;
    }

    public LinkedList<Fireball> getFireballs() {
        return fireballs;
    }

    public  boolean hasState(){
        return userState == null?false:true;
    }
    public void processUserState(Frontend frontend,LongId<UserSession> sessionId,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException{
        userState.processUserState(frontend,sessionId,request,response);
    }
}
