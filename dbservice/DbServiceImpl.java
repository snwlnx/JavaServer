package dbservice;

import base.*;
import frontend.MessageUserIdUpdate;
import user.User;
import user.UserSession;

import java.util.Map;
import java.util.HashMap;


public class DbServiceImpl implements DbService {
    private Address address;
    private MessageSystem msgSystem;

    private Map<String, LongId<User>> userNameToUserId = new HashMap<String, LongId<User>>();

    public DbServiceImpl(MessageSystem msgSystem) {
        this.msgSystem = msgSystem;
        this.address = new Address();

        msgSystem.addService(DbService.class, this);

        this.userNameToUserId.put("Test",  new LongId<User>(1));
        this.userNameToUserId.put("Rus",   new LongId<User>(2));
        this.userNameToUserId.put("User1", new LongId<User>(3));
        this.userNameToUserId.put("User2", new LongId<User>(4));
        this.userNameToUserId.put("User3", new LongId<User>(5));

    }

    public void run() {
        while (true) {
            msgSystem.execForAbonent(this);
            TimeHelper.sleep(20);
        }
    }

    public Address getAddress() {
        return address;
    }

    private LongId<User> getUserId(String name) {
        return this.userNameToUserId.get(name);
    }

    public MessageSystem getMessageSystem() {
        return this.msgSystem;
    }

    public void getUserIdAndAnswer(LongId<UserSession> sessionId, String userName){
        msgSystem.sendMessage(
            new MessageUserIdUpdate(this.getAddress(),
                    msgSystem.getAddress(Frontend.class),
                    sessionId,
                    getUserId(userName)));

    }
}
