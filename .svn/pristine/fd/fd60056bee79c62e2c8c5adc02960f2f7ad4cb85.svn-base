package frontend;

import base.Address;
import base.GameService;
import base.IntegerId;
import base.LongId;
import game.ChatMessage;
import message.MessageToGameService;
import user.User;


public class MessageGameStep extends MessageToGameService {
    final private LongId<User>        userId;


    MessageGameStep(Address from, Address to,LongId<User> userId){
        super(from,to);
        this.userId           = userId;

    }

    public void exec(GameService gameService){
        gameService.doGameStep(userId);
    }


}
