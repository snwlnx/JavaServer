package frontend;

import base.LongId;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import user.User;
import user.UserSession;

public class PageTest extends Assert {

	private static LongId<UserSession> userId;
	private static String formHtml;

	@BeforeClass
	public static void beforeClass() {
		userId = new LongId<UserSession>(1);
		formHtml = "<form method=\"POST\"><input type=\"hidden\" name=\"refresh\" value=\"ok\" />" +
				"<input type=\"submit\" value=\"Попробовать еще!\" /></form>";
	}

	@Test
	public void finishLoseTest() {
		String html = Page.FinishLose(userId);
		assertTrue(html.equals("<body><p>sessionId " + userId.get() + "</p><h1>Ты проиграл, но главное - не расстраивайся!</h1>" + formHtml + "</body>"));
	}

	@Test
	public void refreshFormTest() {
		String finishHtml = Page.FinishWin(userId);
		assertTrue(finishHtml.equals("<body><p>sessionId " + userId.get() + "</p><h1>Ты выиграл! Поиграем еще?</h1>" + formHtml + "</body>"));
	}

}
