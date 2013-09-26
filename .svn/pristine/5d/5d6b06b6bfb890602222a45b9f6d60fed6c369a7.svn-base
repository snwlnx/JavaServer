package game;

import base.Address;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;
import user.UserSession;

public class MessageFinishGameToFrontend extends MessageToFrontend {
    final private   LongId<User>        userToGameSession;
    final private   boolean             win;


    MessageFinishGameToFrontend(Address from, Address to, LongId<User> userToGameSession, boolean win){
        super(from,to);
        this.userToGameSession = userToGameSession;
        this.win = win;
    }

    public void exec(Frontend frontend){
        frontend.finishGame(userToGameSession, win);
    }

}
