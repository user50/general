package general.csv;

/**
 * Created by user50 on 23.01.2015.
 */
public interface LineParser<T> {

    T parse(String line);
}
