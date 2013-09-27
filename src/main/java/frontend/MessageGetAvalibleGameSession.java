package frontend;

import base.Address;
import base.GameService;
import base.LongId;
import message.MessageToGameService;
import user.User;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 28.03.13
 * Time: 13:47
 * To change this template use File | Settings | File Templates.
 */
public class MessageGetAvalibleGameSession extends MessageToGameService {

    final private LongId<User>        userToGameSession;


    public MessageGetAvalibleGameSession(Address from, Address to,LongId<User> userToGameSession){
        super(from, to);
        this.userToGameSession = userToGameSession;
    }

    public  void exec(GameService gameService){
        gameService.getAvailableGameSessionForUser(userToGameSession);
    }




}
