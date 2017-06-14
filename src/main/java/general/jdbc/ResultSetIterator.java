package general.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class ResultSetIterator<T> implements Iterator<T> {

    private ResultSet resultSet;
    private RowMapper<T> dataMapper;

    private boolean hasNext = false;
    private boolean nextCalled = true;

    public ResultSetIterator(ResultSet resultSet, RowMapper<T> dataMapper) {
        this.resultSet = resultSet;
        this.dataMapper = dataMapper;
    }

    @Override
    public boolean hasNext() {
        if (!nextCalled)
            return hasNext;

        try {
            hasNext = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nextCalled = false;

        return hasNext;
    }

    @Override
    public T next() {
        nextCalled = true;

        try {
            return dataMapper.apply(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
