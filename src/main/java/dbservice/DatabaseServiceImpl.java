package dbservice;

import base.Address;
import base.DbService;
import base.LongId;
import base.MessageSystem;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import user.User;
import user.UserSession;

public class DatabaseServiceImpl implements DbService {
    private Address                address;
    private MessageSystem          msgSystem;
    private SessionFactory         sessionFactory;
    private  static ServiceRegistryBuilder builder;

    public void run() {
/*        while (true) {
            msgSystem.execForAbonent(this);
            TimeHelper.sleep(20);
        }*/
    }

    public DatabaseServiceImpl(MessageSystem msgSystem, Configuration configuration,ServiceRegistryBuilder builder) {
        this.msgSystem = msgSystem;
        this.address = new Address();
        DatabaseServiceImpl.builder = builder;
        msgSystem.addService(DbService.class, this);
        configuration.addAnnotatedClass(UserDataSet.class);
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class.", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://192.168.56.101:3306/game");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "123_rootpswd");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public MessageSystem getMessageSystem() {
        return this.msgSystem;
    }

    public Address getAddress() {
        return address;
    }

    public LongId<User> getUserId(String userName) {
        UserDataSetDAO dao = new UserDataSetDAO(sessionFactory);
        try {
            UserDataSet dataSet = dao.read(userName);
            return dataSet.getId();

        } catch (ObjectNotFoundException ex) {
            return null;
        }
    }

    public void getUserIdAndAnswer(LongId<UserSession> sessionId, String userName) {
/*        msgSystem.sendMessage(
                new MessageUserIdUpdate(
                        this.getAddress(),
                        msgSystem.getAddress(Frontend.class),
                        sessionId,
                        getUserId(userName)));*/

    }

}



