package frontend;

import base.Address;
import base.GameService;
import base.LongId;
import message.MessageToGameService;
import user.User;
import user.UserSession;


public class MessageStartGame extends MessageToGameService {

    final private LongId<User> userToGameSession;


    public MessageStartGame(Address from, Address to,LongId<User> userToGameSession){
        super(from, to);
        this.userToGameSession = userToGameSession;

    }

    public  void exec(GameService gameService){
        gameService.startGame(userToGameSession);
    }


}
