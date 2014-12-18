package general.config;

import java.io.IOException;
import java.io.InputStream;

public interface ModulePropertiesStreamProvider {

    public InputStream providePropertiesStream() throws IOException;
}
