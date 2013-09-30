package frontend;

import base.Address;
import base.GameService;
import base.LongId;
import game.GameSession;
import message.MessageToGameService;
import user.User;
import user.UserSession;


public class MessageJoinToGame extends MessageToGameService{

    final private LongId<User>        userToGameSession;
    final private LongId<GameSession> gameSessionId;


    public MessageJoinToGame(Address from, Address to,LongId<User> userToGameSession, LongId<GameSession> gameSessionId){
        super(from, to);
        this.userToGameSession = userToGameSession;
        this.gameSessionId     = gameSessionId;

    }

    public  void exec(GameService gameService){
        gameService.joinToGame(userToGameSession,gameSessionId);
    }

}
