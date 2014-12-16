package general.pool;

import javax.sql.DataSource;

/**
 * Created by user50 on 16.12.2014.
 */
public interface DataSourceProvider {
    DataSource provide();
}
