package general.pool;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created by Yevhen on 12/13/14.
 */
public class ApacheDataSourceProvider implements Provider<DataSource> {

    private BasicDataSource dataSource;

    @Inject
    public ApacheDataSourceProvider(@Named("url") String url, @Named("maxPoolSize") int maxPoolSize) {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setMaxActive(maxPoolSize);
        dataSource.setMaxWait(10000);
        dataSource.setMaxIdle(10);
    }

    @Override
    public DataSource get() {
        return dataSource;
    }
}
