package frontend;

import base.Address;
import base.Fireball;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;

import java.util.LinkedList;

public class MessageFireballsToFrontend extends MessageToFrontend
{
    final private LinkedList<Fireball> fireballs;
    final private LongId<User> userId;

    public MessageFireballsToFrontend(Address from, Address to, LongId<User> userId, LinkedList<Fireball> fireballs) {
        super(from, to);
        this.fireballs = fireballs;
        this.userId = userId;
    }

    public void exec(Frontend frontend){
        frontend.updateFireballs(userId, fireballs);
    }
}
