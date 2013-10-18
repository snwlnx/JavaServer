package frontend;

import base.Address;
import base.GameService;
import base.LongId;
import message.MessageToGameService;
import user.User;

public class MessageRefreshPosition extends MessageToGameService
{
    private int x, y, vX, vY;
    private LongId<User> userId;

    public MessageRefreshPosition(Address from, Address to, LongId<User> userId, int x, int y, int vX, int vY) {
        super(from, to);
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
    }

    public void exec(GameService gameService){
        gameService.updatePosition(userId, x, y, vX, vY);
    }
}
