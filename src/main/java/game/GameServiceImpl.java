package game;

import base.*;
import frontend.MessageFireballsToFrontend;
import frontend.MessageHealthToFrontend;
import frontend.MessageRefreshPositionToFrontend;
import message.Message;
import resource.MapResource;
import user.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


public class GameServiceImpl implements GameService {

    private Address address;
    private MessageSystem messageSystem;
    private AtomicLong gameSessionIdCreator = new AtomicLong(1);
    private Map<LongId<User>, LongId<GameSession>> userIdToGameSessionId = new HashMap<LongId<User>, LongId<GameSession>>();
    private Map<LongId<GameSession>, GameSession> gameIdToGameSession = new HashMap<LongId<GameSession>, GameSession>();
    private ResourceSystem resourceSystem;

    private int counter = 0;
    private int mapWidth, mapHeight;


    public GameServiceImpl(MessageSystem messageSystem, ResourceSystem resources) {
        resourceSystem = resources;

        MapResource mResource = (MapResource)resourceSystem.getResource(MapResource.class);
        mapHeight = mResource.height;
        mapWidth  = mResource.width;



        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(GameService.class, this);
    }


    public void run() {
        for (counter = 0; true; ++counter) {
            messageSystem.execForAbonent(this);

            calculateGame();
            replicatePositionsToFrontend();

            clearGames();

            // ~ 60 FPS
            TimeHelper.sleep(17);
            if(counter > 10000) {
                counter = 0;
            }
        }
    }

    private void clearGames() {
        if(counter % 60 != 0) {
            return;
        }
        Iterator<LongId<GameSession>> iter = gameIdToGameSession.keySet().iterator();
        LongId<GameSession> gameId;
        GameSession game;
        LinkedList<LongId<GameSession>> gamesForDelete = new LinkedList<LongId<GameSession>>();

        while(iter.hasNext()) {
            gameId = iter.next();
            game = gameIdToGameSession.get(gameId);

            if(game.getAllPlayers().isEmpty()) {
                gamesForDelete.add(gameId);
            }
        }
        if(gamesForDelete.isEmpty() == false) {
            for(LongId<GameSession> id : gamesForDelete) {
                gameIdToGameSession.remove(id);
            }
        }
    }

    private void calculateGame() {
        Iterator<GameSession> iter = gameIdToGameSession.values().iterator();
        GameSession game;

        while (iter.hasNext()) {
            game = iter.next();
            calculatePlayers(game);
            calculateFireballs(game);
        }
    }

    private void calculatePlayers(GameSession game) {
        Map<LongId<User>, Player> users = game.getAllPlayers();
        Set<LongId<User>> ids = users.keySet();
        Player player;

        for(LongId<User> id : ids) {
            player = users.get(id);

            if(player.getPositionY() > mapHeight) {
                playerLose(game, users, id);
            }
        }
    }

    private void calculateFireballs(GameSession game) {
        LinkedList<Fireball> fballs = new LinkedList<Fireball>();
        LinkedList<Fireball> bls = game.getFireballs();

        int x, y;
        boolean changes = false;

        for (Fireball fb : bls) {
            fb.move();
            x = fb.getPositionX();
            if (x < 0 || x > mapWidth) {
                changes = true;
                continue;
            }
            y = fb.getPositionY();
            if (y < 0 || y > mapHeight) {
                changes = true;
                continue;
            }
            if (collisionWithPlayers(game, x, y)) {
                changes = true;
                continue;
            }
            fballs.add(fb);
        }

        // если изменился массив, то обновляем его
        if (changes) {
            game.setFireballs(fballs);
        }
        // копируем для отправки во фронтенд
        LinkedList<Fireball> fballsToFrontend = new LinkedList<Fireball>();
        for (Fireball f : fballs) {
            fballsToFrontend.add(f.clone());
        }
        // отправляем всем юзерам-участникам
        Set<LongId<User>> users = game.getAllUserId();
        Address from = this.getAddress();
        for (LongId<User> userId : users) {
            Message message = new MessageFireballsToFrontend(
                    from,
                    messageSystem.getAddress(Frontend.class),
                    userId,
                    fballsToFrontend);
            messageSystem.sendMessage(message);
        }
    }

    private boolean collisionWithPlayers(GameSession game, int x, int y) {
        Map<LongId<User>, Player> users = game.getAllPlayers();
        Set<LongId<User>> ids = users.keySet();
        int damage = Fireball.getDamage(),
                health;
        Message message;

        for (LongId<User> id : ids) {
            Player pl = users.get(id);
            if (pl.collision(x, y)) {
                health = pl.hurt(damage);
                if (pl.isAlive()) {
                    message = new MessageHealthToFrontend(
                            address,
                            messageSystem.getAddress(Frontend.class),
                            id,
                            health);
                    messageSystem.sendMessage(message);
                }
                else {
                    playerLose(game, users, id);
                }
                return true;
            }
        }
        return false;
    }

    private void playerLose(GameSession game, Map<LongId<User>, Player> users, LongId<User> id) {
        Set<LongId<User>> ids = users.keySet();
        users.remove(id);
        userIdToGameSessionId.remove(id);

        // оповещаем игрока о том, что он сдох
        Message message = new MessageFinishGameToFrontend(
                address,
                messageSystem.getAddress(Frontend.class),
                id,
                false);
        messageSystem.sendMessage(message);

        if(users.size() == 1) {
            // отправляем, что другой игрок выиграл!
            LongId<User> winner = users.keySet().iterator().next();

            message = new MessageFinishGameToFrontend(
                    address,
                    messageSystem.getAddress(Frontend.class),
                    winner,
                    true);
            messageSystem.sendMessage(message);

            // удаляем последнего игрока
            users.remove(winner);
            userIdToGameSessionId.remove(winner);
        }
        else {
            // оповещаем опонентов о смене количества игроков
            for (LongId<User> userIdToFrontend : ids) {
                if (userIdToFrontend != id) {
                    message = new MessageRefreshPositionToFrontend(
                            address,
                            messageSystem.getAddress(Frontend.class),
                            userIdToFrontend,
                            game.getEnemiesArray(userIdToFrontend));
                    messageSystem.sendMessage(message);
                }
            }
        }
    }

    private void replicatePositionsToFrontend() {
        Iterator<GameSession> iterGameSession = gameIdToGameSession.values().iterator();

        while (iterGameSession.hasNext()) {
            GameSession game = iterGameSession.next();
            Set<LongId<User>> users = game.getAllUserId();
            Message message;

            for (LongId<User> userId : users) {
                LinkedList<Player> enems = game.getEnemiesArray(userId);
                if (enems.isEmpty() == false) {
                    message = new MessageRefreshPositionToFrontend(
                            address,
                            messageSystem.getAddress(Frontend.class),
                            userId,
                            enems
                    );
                    messageSystem.sendMessage(message);
                }
            }
        }
    }


	public LongId<GameSession> startGame(LongId<User> userToGameSession) {
        LongId<GameSession> gameSessionId = null;
        if (!userIdToGameSessionId.containsKey(userToGameSession)) {
            gameSessionId = addNewGameSession(userToGameSession);
            userIdToGameSessionId.put(userToGameSession, gameSessionId);
            replicateStartGameToFrontend(userToGameSession, gameSessionId);
        }
		return gameSessionId;
    }


    private LongId<GameSession> addNewGameSession(LongId<User> userToGameSession) {
        GameSession gameSession = new GameSession(userToGameSession);

        gameSession.addNewPlayer(userToGameSession);

        LongId<GameSession> gameSessionId = new LongId<GameSession>(gameSessionIdCreator.getAndIncrement());
        gameIdToGameSession.put(gameSessionId, gameSession);
        return gameSessionId;
    }

    private void replicateStartGameToFrontend(LongId<User> userToGameSession, LongId<GameSession> gameSessionId) {
        Message message = new MessageStartGameToFrontend(
                this.getAddress(),
                messageSystem.getAddress(Frontend.class),
                userToGameSession,
                gameSessionId);
        messageSystem.sendMessage(message);
    }

    public boolean joinToGame(LongId<User> userToGameSession, LongId<GameSession> gameSessionId) {
        GameSession game = gameIdToGameSession.get(gameSessionId);
        if (game != null) {
	        userIdToGameSessionId.put(userToGameSession, gameSessionId);
            game.addIndexLastMsg(userToGameSession);
            game.addNewPlayer(userToGameSession);
        }
        replicateJoinGameToFrontend(userToGameSession);
	    return game != null;
    }

    private void replicateJoinGameToFrontend(LongId<User> userToGameSession) {
        Message message = new MessageJoinGameToFrontend(
                this.getAddress(),
                messageSystem.getAddress(Frontend.class),
                userToGameSession);
        messageSystem.sendMessage(message);
    }


    public void doGameStep(LongId<User> userId) {
        GameSession gameSession = gameIdToGameSession.get(userIdToGameSessionId.get(userId));
        ChatMessage[] lastMessages = gameSession.getMessagesForUser(userId);
        if (lastMessages != null) {
            replicateGameStepToFrontend(userId, lastMessages);
        }
    }

    private void replicateGameStepToFrontend(LongId<User> userId, ChatMessage[] chatMessages) {
        Message message = new MessageReplicateGameToFrontend(
                this.getAddress(),
                messageSystem.getAddress(Frontend.class),
                userId,
                chatMessages);
        messageSystem.sendMessage(message);
    }


    public void doGameAction(LongId<User> userId, String textForChatMsg) {
        LongId<GameSession> gameSessionId = userIdToGameSessionId.get(userId);
        gameIdToGameSession.get(gameSessionId).addMessageToChat(userId, textForChatMsg);
    }


    public void finishGame(LongId<User> userToGameSession) {
        userIdToGameSessionId.remove(userToGameSession);
        replicateFinishGameToFrontend(userToGameSession);
    }


    private void replicateFinishGameToFrontend(LongId<User> userToGameSession) {
        Message message = new MessageFinishGameToFrontend(
                address,
                messageSystem.getAddress(Frontend.class),
                userToGameSession,
                true);
        messageSystem.sendMessage(message);
    }

    public void getAvailableGameSessionForUser(LongId<User> userToGameSession) {
        replicateAvailableGameSessionForUser(userToGameSession, gameIdToGameSession.keySet());
    }

    private void replicateAvailableGameSessionForUser(LongId<User> userToGameSession,
                                                      Set<LongId<GameSession>> availableGameSessionsId) {
        Message message = new MessageUpdateAvalibleGameSession(
                this.getAddress(),
                messageSystem.getAddress(Frontend.class),
                userToGameSession,
                availableGameSessionsId);
        messageSystem.sendMessage(message);
    }


    public void refreshPosition(LongId<User> userId, int x, int y, int vX, int vY) {
        LongId<GameSession> gameSessionId = this.userIdToGameSessionId.get(userId);
        if (gameSessionId != null) {
            GameSession sess = this.gameIdToGameSession.get(gameSessionId);
            if (sess != null) {
                Player pl = sess.getPlayer(userId);
                if (pl != null) {
                    pl.setPosition(x, y);
                    pl.setVelocity(vX, vY);
                }
            }
        }
    }

    public void addFireball(LongId<User> userId, Fireball fb) {

        LongId<GameSession> gameId = userIdToGameSessionId.get(userId);
        if (gameId != null) {
            GameSession game = gameIdToGameSession.get(gameId);
            if (game != null) {

                game.addFireball(fb);
                //System.out.println("FIREBALLS: " + game.getFireballs().size());
                //Iterator<LongId<User>> iter = game.getAllUserId().iterator();
                //LongId<User> uId = null;
                //while (iter.hasNext()) {
                //    uId = iter.next();
                //
                //    // todo отправить на фронтенд реплику
                //}
            }
        }
    }


    public Address getAddress() {
        return this.address;
    }

    public MessageSystem getMessageSystem() {
        return this.messageSystem;
    }
}
