package game;

import base.Address;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 28.03.13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class MessageUpdateAvalibleGameSession extends MessageToFrontend {

    final private    LongId<User>                  userToGameSession;
    final private    Set< LongId < GameSession > > avalibleGameSessionsId;

    MessageUpdateAvalibleGameSession(Address from, Address to, LongId<User> userToGameSession,
                                     Set< LongId < GameSession > > avalibleGameSessionsId){

            super(from,to);
            this.userToGameSession          = userToGameSession;
            this.avalibleGameSessionsId     = avalibleGameSessionsId;
        }

        public void exec(Frontend frontend){
            frontend.updateAvailableGameSessionForUser(userToGameSession, avalibleGameSessionsId);
        }

}
