package frontend;

import base.Address;
import base.GameService;
import base.LongId;
import base.Fireball;
import message.MessageToGameService;
import user.User;

public class MessageAddFireball extends MessageToGameService
{
    final private Fireball fireball;
    final private LongId<User> userId;

    public MessageAddFireball(Address from, Address to, LongId<User> userId, Fireball fireball) {
        super(from, to);
        this.fireball = fireball;
        this.userId   = userId;
    }

    public void exec(GameService gameService){
        System.out.println(fireball);
        gameService.addFireball(userId, fireball);
    }
}
