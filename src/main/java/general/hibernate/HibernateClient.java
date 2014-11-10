package general.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateClient {
    private SessionFactory sessionFactory;

    private String host;
    private String user;
    private String psw;
    private String database;

    public HibernateClient(String host, String user, String psw, String database) {
        this.host = host;
        this.user = user;
        this.psw = psw;
        this.database = database;


        try {
            Configuration configuration = new Configuration();
            addAnnotatedClasses( configuration );
            addProperties( configuration );
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(ssrb.build());
        } catch (ExceptionInInitializerError ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void addAnnotatedClasses(Configuration configuration)
    {
//        configuration.addAnnotatedClass( ClientSettings.class )
    }

    private void addProperties(Configuration configuration)
    {
        configuration.setProperty( "hibernate.connection.url", "jdbc:mysql://"+host+":3306/"+database+"?useUnicode=yes&characterEncoding=utf8" )
                .setProperty( "hibernate.connection.username", user )
                .setProperty( "hibernate.connection.password", psw )
                .setProperty( "hibernate.connection.autocommit", "true" )
                .setProperty( "show_sql", "true" )
                .setProperty( "dialect", "org.hibernate.dialect.MySQLDialect" )
                .setProperty( "hibernate.c3p0.min_size", "2" )
                .setProperty( "hibernate.c3p0.max_size", "2" )
                .setProperty( "hibernate.c3p0.timeout", "1800" )
                .setProperty( "hibernate.c3p0.max_statements", "50" );
//                .setProperty("hibernate.hbm2ddl.auto", "update");
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
        public <T> T execute(Session session);
    }

    public static interface Update
    {
        public void execute(Session session);
    }
}