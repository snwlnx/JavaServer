package game;

import base.Address;
import base.Frontend;
import base.LongId;
import base.Player;
import message.MessageToFrontend;
import user.User;

import java.util.LinkedList;

public class MessageRefreshPositionToFrontend extends MessageToFrontend {
    final private LongId<User> userId;
    final private LinkedList<Player> enemies;

    public MessageRefreshPositionToFrontend(Address from, Address to, LongId<User> userId,  LinkedList<Player> players) {
        super(from, to);
        this.userId = userId;
        enemies = players;
    }

    public void exec(Frontend frontend) {
        frontend.refreshPosition(userId, enemies);
    }
}
