package frontend;

import base.LongId;
import game.ChatMessage;
import game.GameSession;
import user.User;
import user.UserSession;

import java.util.Iterator;
import java.util.Set;

public class Page {
    public static String Start(LongId<UserSession> sessionId) {
        StringBuilder str = new StringBuilder("");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<form id=\"MainForm\" method=\"POST\">");
        str.append("<label>UserName <input type=\"text\" name=\"user_name\" /></label>");
        str.append("<input type=\"submit\" value=\"Submit\" />");
        str.append("</form>");
        str.append("</body>");

        return str.toString();
    }

    public static String FinishLose(LongId<UserSession> sessionId) {
        StringBuilder str = new StringBuilder("");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<h1>Ты проиграл, но главное - не расстраивайся!</h1>");
	    refreshForm(str);
        str.append("</body>");
        return str.toString();
    }

    private static void refreshForm(StringBuilder str) {
        str.append("<form method=\"POST\"><input type=\"hidden\" name=\"refresh\" value=\"ok\" />");
        str.append("<input type=\"submit\" value=\"Попробовать еще!\" /></form>");
    }

    public static String FinishWin(LongId<UserSession> sessionId) {
        StringBuilder str = new StringBuilder("");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<h1>Ты выиграл! Поиграем еще?</h1>");
        refreshForm(str);
        str.append("</body>");

        return str.toString();
    }

    public static String WaitForAuthorization(LongId<UserSession> sessionId, String userName) {
        StringBuilder str = new StringBuilder("");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<p>userName ").append(userName).append("</p>");
        str.append("<p>Wait some seconds for auth!!!</p>");
        str.append("<form id=\"MainForm\" method=\"POST\">");
        str.append("<input type=\"hidden\" name=\"session\" value=\"").append(sessionId.get()).append("\" />");

        str.append("</form><script>(function () {setInterval(function () { window.location.reload(); }, 1000)})();</script>");
        //str.append(Page.FormUpdater());
        str.append("</body>");

        return str.toString();
    }

    public static String AuthAccept(LongId<UserSession> sessionId, String userName, LongId<User> userId) {
        StringBuilder str = new StringBuilder("");

        str.append("<html><head>");
        str.append("<script src=\"js/jquery.js?1.9.1\"></script>");
        str.append("</head>");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<p>userName ").append(userName).append("</p>");
        str.append("<p>userId ").append(userId.get()).append("</p>");


        str.append("<form id=\"MainForm\" method=\"POST\">");
        str.append("<input type=\"hidden\" name=\"session\" value=\"").append(sessionId.get()).append("\" />");
        str.append("<input type=\"submit\" name=\"New\" value=\"New\" />");
        str.append("<p id='ppp'><button id='butt' type='button'>Показать доступные игры</button>");
        str.append("</form>");

        str.append("<script src=\"js/authaccept.js?0.1\"></script>");

        str.append("</body></html>");

        return str.toString();
    }

    public static String PlayJQuery(LongId<UserSession> sessionId, String userName) {
        StringBuilder str = new StringBuilder("");
        str.append("<html><head>");
        str.append("<script src=\"js/jquery.js?1.9.1\"></script>");

        str.append("</head>");
        str.append("<body>");
        str.append("<h1>Contra Online <span id=\"fps\"></span></h1>");
        str.append("<p>Health <span id=\"health\"></span></p>");
        //str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        //str.append("<p>userName ").append(userName).append("</p>");

        str.append("<canvas id=\"paper\"></canvas>");

        str.append("<form id=\"MainForm\" method=\"POST\">");
        str.append("<p>Введите сообщение<Br>");
        // str.append("<input type=\"hidden\" name=\"gameSessionId\" value=\"").append(gameSessionId.get()).append("\" />");
        str.append("<p id = 'message'>s<p>");
        str.append("<textarea name='message' id='msg' cols='40' rows='3'></textarea></p>");
        //str.append("<p><input type='submit'  value ='Отправить'>");
        str.append("<p><button id='butt' type='button'>Нажми</button>");


        str.append("</form>");
        // todo МЕНЯТЬ ВЕРСИЮ JS ТУТ
        str.append("<script src=\"js/game.js?51\"></sawcript>");
        str.append("<script src=\"js/play.js?51\"></script>");
        str.append("</body></html>");
        return str.toString();
    }


    public static String Play(LongId<UserSession> sessionId, String userName) {
        StringBuilder str = new StringBuilder("");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<p>userName ").append(userName).append("</p>");

        str.append("<form id=\"MainForm\" method=\"POST\">");
        str.append("<p>Введите сообщение<Br>");
        // str.append("<input type=\"hidden\" name=\"gameSessionId\" value=\"").append(gameSessionId.get()).append("\" />");
        str.append("<textarea name='message' cols='40' rows='3'></textarea></p>");
        str.append("<p><input type='submit'  value ='Отправить'>");
        str.append("<input type=\"submit\" name=\"Exit\" value=\"Exit\" />");

        str.append("</form>");
        str.append("</body>");

        return str.toString();


    }


    public static String NotAuthorized(LongId<UserSession> sessionId, String userName) {
        StringBuilder str = new StringBuilder("");

        str.append("<body>");

        str.append("<p>sessionId ").append(sessionId.get()).append("</p>");
        str.append("<p>userName ").append(userName).append("</p>");
        str.append("<p>You not Authorized!!!</p>");
        str.append("<form id=\"MainForm\" method=\"POST\">");

        str.append("</form>");
        str.append("<script>(function () {setTimeout( function () {window.location.reload(); }, 2000);})();</script>");
        str.append("</body>");

        return str.toString();
    }
}
