package dbservice;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDataSetDAO {
    private SessionFactory sessionFactory;

    public UserDataSetDAO(SessionFactory session) {
        this.sessionFactory = session;
    }

    public void save(UserDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dataSet);
        transaction.commit();
        session.close();
    }

    public UserDataSet read(long id) {
        Session session = sessionFactory.openSession();
        return (UserDataSet)session.load(UserDataSet.class, id);
    }

    public UserDataSet read(String userName) {
        Session session = sessionFactory.openSession();
        return (UserDataSet)session.load(UserDataSet.class, userName);
    }

}
