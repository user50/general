package general.jdbc;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JdbcServiceUtil {

    public static String getQuestionMarkLine(int questionMarkCount)
    {
        String[] array = new String[questionMarkCount];
        Arrays.fill(array, "?");

        return Arrays.asList(array).stream().collect(Collectors.joining(","));
    }

}
