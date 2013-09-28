package frontend;

import base.*;
import message.MessageToDbService;
import user.UserSession;

public class MessageUserIdGet extends MessageToDbService {
    private LongId<UserSession> sessionId;
    private String userName;

    public MessageUserIdGet(Address from, Address to, LongId<UserSession> sessionId, String userName) {
        super(from, to);
        this.userName = userName;
        this.sessionId = sessionId;
    }

    public void exec(DbService dbServ) {
        System.out.println(userName + "_" + sessionId.get());
        dbServ.getUserIdAndAnswer(sessionId,userName);
    }
}
