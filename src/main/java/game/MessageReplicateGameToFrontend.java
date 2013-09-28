package game;

import base.Address;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;

public class MessageReplicateGameToFrontend extends MessageToFrontend{
    final private LongId<User>  userId;
    final private ChatMessage[] lastMessages;


    MessageReplicateGameToFrontend(Address from, Address to,LongId<User> userId, ChatMessage[] lastMessages){
        super(from,to);
        this.userId       = userId;
        this.lastMessages = lastMessages;
    }

    public void exec(Frontend frontend){
        frontend.updateGameStep(userId, lastMessages);
    }
}
