package frontend;

import base.Address;
import base.GameService;
import base.LongId;
import message.MessageToGameService;
import user.User;

public class MessageFinishGame extends MessageToGameService {
    final private   LongId<User> usersToGameSession;

    MessageFinishGame(Address from,Address to,LongId<User> usersToGameSession){
        super(from,to);
        this.usersToGameSession = usersToGameSession;

    }

    public void exec(GameService gameService) {
        gameService.finishGame(usersToGameSession);
    }
}
