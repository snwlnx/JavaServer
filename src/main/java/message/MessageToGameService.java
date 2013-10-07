package message;

import base.Abonent;
import base.Address;
import base.GameService;

public abstract class MessageToGameService extends Message{
    public MessageToGameService(Address from, Address to) {
        super(from, to);
    }

    public boolean exec(Abonent abonent) {
        if(abonent instanceof GameService) {
            exec((GameService) abonent);
	        return true;
        }
	    return false;
    }

    public abstract void exec(GameService gameService);
}
