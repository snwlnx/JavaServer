package main;

import base.GameService;
import base.MessageSystem;
import base.ResourceSystem;
import dbservice.DbServiceImpl;
import frontend.FrontendImpl;
import game.GameServiceImpl;
import message.MessageSystemImpl;
import org.eclipse.jetty.server.Server;
import resource.ResourceSystemImpl;

public class Main {
    public static void main(String[] args) throws Exception {

        MessageSystem msgSystem        = new MessageSystemImpl();
        ResourceSystem resourceSystem  = new ResourceSystemImpl();
        resourceSystem.globalInit();

        FrontendImpl    frontend       = new FrontendImpl(msgSystem);
        GameService     gameService    = new GameServiceImpl(msgSystem,resourceSystem);
        DbServiceImpl   dbService      = new DbServiceImpl(msgSystem);
       // DbService  dbService            = new DatabaseServiceImpl(msgSystem);

        (new Thread(dbService)).start();
        (new Thread(gameService)).start();
        (new Thread(frontend)).start();

        Server server = new Server(8080);
        server.setHandler(frontend);

        server.start();
        server.join();
    }
}