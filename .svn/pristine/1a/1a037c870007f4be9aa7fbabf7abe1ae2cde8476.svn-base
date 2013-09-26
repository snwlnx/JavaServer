package frontend;

import base.Address;
import base.Frontend;
import base.LongId;
import base.Player;
import message.MessageToFrontend;
import message.MessageToGameService;
import user.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

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
