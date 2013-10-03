package game;

import base.Address;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;


public class MessageStartGameToFrontend extends MessageToFrontend{
    final private   LongId<User>        userToGameSession;
    final private   LongId<GameSession> gameSessionId;

    public MessageStartGameToFrontend(Address from, Address to, LongId<User> userToGameSession,
                                                         LongId<GameSession> gameSessionId){
        super(from,to);
        this.userToGameSession = userToGameSession;
        this.gameSessionId     = gameSessionId;
    }

    public void exec(Frontend frontend){
        frontend.startGame(userToGameSession,gameSessionId);
    }
}
