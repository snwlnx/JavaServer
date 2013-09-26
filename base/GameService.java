package base;


import game.GameSession;
import user.User;

public interface GameService extends Abonent,Runnable {
    public void startGame                    ( LongId < User > userToGame );
    public void joinToGame                   ( LongId < User > userToGameSession, LongId < GameSession > gameSessionId );
    public void doGameStep                   ( LongId < User > userId);
    public void doGameAction                 ( LongId < User > userId, String textForChatMsg );
    public void finishGame                   ( LongId < User > userToGameSession );
    public void getAvailableGameSessionForUser(LongId<User> userToGameSession);
    public void refreshPosition(LongId<User> userId, int x, int y, int vX, int vY);

    public void addFireball(LongId<User> userId, Fireball fb);
}
