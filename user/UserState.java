package user;

import base.Frontend;
import base.LongId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 8/18/13
 * Time: 11:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserState {

    public void processUserState(Frontend frontend,LongId<UserSession> sessionId,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException;

}
