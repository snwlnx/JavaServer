package game;

import base.Abonent;
import base.Frontend;
import base.LongId;
import base.MessageSystem;
import frontend.FrontendImpl;
import user.User;
import user.UserSession;

public class FrontendMock extends FrontendImpl implements Frontend, Abonent {

	public FrontendMock(MessageSystem messageSystem) {
		super(messageSystem);

		LongId<UserSession> user1 =   new LongId<UserSession>(11111111);
		LongId<UserSession> user2 =   new LongId<UserSession>(99999999);


		this.addUserSession(user1, "user111");
		this.addUserSession(user2, "user999");

		this.addUserIdToSession(new LongId<User>(111), user1);
		this.addUserIdToSession(new LongId<User>(999), user2);
	}
}
