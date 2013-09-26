package game;

import base.Address;
import base.Frontend;
import base.LongId;
import base.Player;
import message.MessageToFrontend;
import user.User;
import user.UserSession;


public class MessageJoinGameToFrontend extends MessageToFrontend {
    final private   LongId<User>        userToGameSession;

    MessageJoinGameToFrontend(Address from, Address to, LongId<User> userToGameSession){
        super(from,to);
        this.userToGameSession = userToGameSession;
    }

    public void exec(Frontend frontend){
        frontend.joinToGame(userToGameSession);
    }
}
