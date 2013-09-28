package frontend;

import base.Address;
import base.Frontend;
import base.LongId;
import message.MessageToFrontend;
import user.User;
import user.UserSession;


public class MessageUserIdUpdate extends MessageToFrontend {

    final private LongId<UserSession> sessionId;
    final private LongId<User> userId;

    public MessageUserIdUpdate(Address from, Address to, LongId<UserSession> sessionId, LongId<User> userId) {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public void exec (Frontend frontend) {
        frontend.updateUserId(sessionId, userId);
    }
}
