package general.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.sql.DataSource;

public class HibernateClient {
    private SessionFactory sessionFactory;

    public HibernateClient(DataSource dataSource, Configuration configuration) {
        try {
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(ssrb.applySetting( Environment.DATASOURCE, dataSource ).build());

        } catch (ExceptionInInitializerError ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Gets hiberante session factory that was initialized at application startup.
     *
     * @return hibernate session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public <T> T execute(Query query)
    {
        Session session = getSessionFactory().openSession();
        T result = (T) query.execute(session);
        session.flush();
        session.close();

        return result;
    }

    public void execute(Update update)
    {
        Session session = getSessionFactory().openSession();
        update.execute(session);
        session.flush();
        session.close();
    }

    public static interface Query<T>
    {
        public T execute(Session session);
    }

    public static interface Update
    {
        public void execute(Session session);
    }
}