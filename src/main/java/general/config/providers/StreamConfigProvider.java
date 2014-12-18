package general.config.providers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StreamConfigProvider extends AbstractConfigProvider {

    private static final Logger LOGGER = Logger.getLogger(StreamConfigProvider.class);

    private final Properties properties;

    public StreamConfigProvider(ConfigStreamProvider streamProvider){
        this.properties = load(streamProvider);
    }

    private Properties load(ConfigStreamProvider streamProvider){
        Properties props = new Properties();
        try(InputStream in = streamProvider.providePropertiesStream()) {
            props.load(in);
        } catch (IOException e) {
            LOGGER.error("Failed load properties : ", e);
        }
        return props;
    }

    @Override
    protected Object getOptionalProperty(String name) {
        return properties.getProperty(name);
    }
}
