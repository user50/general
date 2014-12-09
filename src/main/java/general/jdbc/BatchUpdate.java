package general.jdbc;

/**
 * Created by user50 on 09.12.2014.
 */
public interface BatchUpdate extends SqlOperation {

    boolean hasNext();

    int executeBatchEvery();

}
