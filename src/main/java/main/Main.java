package main;

import base.*;

import dbservice.DatabaseServiceImpl;
import dbservice.DbServiceImpl;
import frontend.FrontendImpl;
import game.GameServiceImpl;
import message.MessageSystemImpl;
import org.eclipse.jetty.server.Server;
import resource.FightResource;
import resource.ResourceSystemImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        MessageSystem msgSystem = new MessageSystemImpl();

        // todo ресурсная система


        ResourceSystem resourceSystem = new ResourceSystemImpl();
        resourceSystem.globalInit();


        FrontendImpl    frontend       = new FrontendImpl(msgSystem);
        GameService     gameService       = new GameServiceImpl(msgSystem,resourceSystem);
        DbServiceImpl dbService        = new DbServiceImpl(msgSystem);
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