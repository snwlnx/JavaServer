package base;

import user.UserSession;

public interface DbService extends Abonent,Runnable {
   public void getUserIdAndAnswer(LongId<UserSession> sessionId, String userName);
}
