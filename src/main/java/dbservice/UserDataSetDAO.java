package dbservice;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDataSetDAO {
    private SessionFactory sessionFactory;

    public UserDataSetDAO(SessionFactory session) {
        this.sessionFactory = session;
    }

    public UserDataSet read(String userName) {
        Session session = sessionFactory.openSession();
        return (UserDataSet)session.load(UserDataSet.class, userName);
    }

}
