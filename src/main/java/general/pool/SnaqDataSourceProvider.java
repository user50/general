package general.pool;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import snaq.db.DBPoolDataSource;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 12/13/14.
 */
public class SnaqDataSourceProvider implements Provider<DataSource> {

    private DBPoolDataSource dataSource;

    @Inject
    public SnaqDataSourceProvider(@Named("url") String url, @Named("maxPoolSize") int maxPoolSize) {
        dataSource = new DBPoolDataSource();
        dataSource.setName("pool-ds");
        dataSource.setDescription("Pooling DataSource");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setMinPool(1);
        dataSource.setMaxPool(maxPoolSize);
        dataSource.setIdleTimeout(3600);  // Specified in second.
        dataSource.setValidationQuery("SELECT 1 ");
    }

    @Override
    public DataSource get() {
        return dataSource;
    }
}
