package general.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import general.jdbc.JdbcService;
import general.pool.ApacheDataSourceProvider;

import javax.inject.Singleton;
import javax.sql.DataSource;


/**
 * Created by user50 on 16.01.2015.
 */
public class JdbcServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JdbcService.class).in(Singleton.class);
        bind(DataSource.class).toProvider(ApacheDataSourceProvider.class);

        bind(String.class).annotatedWith(Names.named("url"))
                .toInstance("jdbc:mysql://78.140.179.45:3306/aligator_engine_deposit?user=deposit&password=photos&allowMultiQueries=true");
        bind(Integer.class).annotatedWith(Names.named("maxPoolSize")).toInstance(10);
    }
}
