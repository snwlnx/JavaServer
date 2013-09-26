package frontend;

import base.Address;
import base.Fireball;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;

import java.util.LinkedList;

public class MessageHealthToFrontend extends MessageToFrontend
{
    final private int health;
    final private LongId<User> userId;

    public MessageHealthToFrontend(Address from, Address to, LongId<User> userId, int health) {
        super(from, to);
        this.health = health;
        this.userId = userId;
    }

    public void exec(Frontend frontend){
        frontend.updateHealth(userId, health);
    }
}
