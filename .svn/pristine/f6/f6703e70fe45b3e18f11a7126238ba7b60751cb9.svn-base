package frontend;

import base.Address;
import base.Frontend;
import base.GameService;
import base.LongId;
import message.MessageToFrontend;
import message.MessageToGameService;
import user.User;


public class MessageGameAction extends MessageToGameService {
    final private LongId<User> userId;
    final private String       textForChatMsg;


    MessageGameAction(Address from,Address to,LongId<User> userId,String textForChatMsg){
        super(from,to);
        this.userId         = userId;
        this.textForChatMsg = textForChatMsg;

    }

    public void exec(GameService gameService){
        gameService.doGameAction(userId,textForChatMsg);
    }


}