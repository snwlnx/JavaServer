package game;

import base.Fireball;
import base.IntegerId;
import base.LongId;
import base.Player;
import user.User;

import java.util.*;

public class GameSession {

    private ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
    private Map<LongId<User>, IntegerId<ChatMessage>> userIdToLastChatMessage = new HashMap<LongId<User>, IntegerId<ChatMessage>>();
    private GameMap map;

    // сама игра
    private Map<LongId<User>, Player> userIdToPlayer = new HashMap<LongId<User>, Player>();
    private LinkedList<Fireball> fireballs = new LinkedList<Fireball>();

    GameSession(LongId<User> userId) {
        userIdToLastChatMessage.put(userId, new IntegerId<ChatMessage>(0));
    }


    public void addMessageToChat(LongId<User> userId, String textForChatMsg) {
        ChatMessage chatMsg = new ChatMessage(userId, textForChatMsg);
        chatMessages.add(chatMsg);
    }

    public void addIndexLastMsg(LongId<User> userId) {
        userIdToLastChatMessage.put(userId, new IntegerId<ChatMessage>(0));
    }


    public ChatMessage[] getMessagesForUser(LongId<User> userId) {

        IntegerId<ChatMessage> lastIndexForUser = userIdToLastChatMessage.get(userId);
        if (lastIndexForUser == null)
            return null;
        Integer indexDifference = chatMessages.size() - lastIndexForUser.get();
        ChatMessage[] newMessages = null;

        if (indexDifference > 0) {
            newMessages = new ChatMessage[indexDifference];
            for (int i = 0; i < indexDifference; i++)
                newMessages[i] = chatMessages.get(chatMessages.size() - indexDifference + i);
            lastIndexForUser.set((lastIndexForUser.get() + indexDifference));

        }
        return newMessages;
    }

    public Player getPlayer(LongId<User> userId) {
        return this.userIdToPlayer.get(userId);
    }

    public Set<LongId<User>> getAllUserId() {
        return userIdToPlayer.keySet();
    }

    public LinkedList<Player> getEnemiesArray(LongId<User> userId) {
        LinkedList<Player> enems = new LinkedList<Player>();
        Iterator<LongId<User>> iter = userIdToPlayer.keySet().iterator();
        LongId<User> uId;

        while(iter.hasNext()) {
            uId = iter.next();
            if(uId != userId) {
                enems.add(userIdToPlayer.get(uId));
            }
        }
        return enems;
    }

    public Player addNewPlayer(LongId<User> userId) {
        if (userIdToPlayer.containsKey(userId) == false) {
            // todo координаты из балды
            Player pl = new Player(100, 100);
            userIdToPlayer.put(userId, pl);

            System.out.println("IN GAME: " + userIdToPlayer.size());
            return pl;
        }
        System.out.println("ERROR GameSession.addNewPlayer( " + userId + " s)");
        return null;
    }

    public Map<LongId<User>, Player> getAllPlayers() {
        return userIdToPlayer;
    }

    public void addFireball(Fireball fb) {
        fireballs.add(fb);
    }

    public void setFireballs( LinkedList<Fireball> fballs) {
        fireballs = fballs;
    }

    public LinkedList<Fireball> getFireballs() {
        return fireballs;
    }
}
