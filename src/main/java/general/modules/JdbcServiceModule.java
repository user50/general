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

    JdbcServiceModuleConfig config;

    public JdbcServiceModule(JdbcServiceModuleConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(JdbcService.class).in(Singleton.class);
        bind(DataSource.class).toProvider(ApacheDataSourceProvider.class);

        bind(String.class).annotatedWith(Names.named("url"))
                .toInstance(config.getUrl());
        bind(Integer.class).annotatedWith(Names.named("maxPoolSize")).toInstance(config.getMaxPoolSize());
    }
}
