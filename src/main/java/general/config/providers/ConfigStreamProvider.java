package general.config.providers;

import java.io.IOException;
import java.io.InputStream;

public interface ConfigStreamProvider {

    public InputStream providePropertiesStream() throws IOException;
}
