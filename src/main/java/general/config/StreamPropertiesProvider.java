package general.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StreamPropertiesProvider extends AbstractPropertiesProvider {

    private static final Logger LOGGER = Logger.getLogger(StreamPropertiesProvider.class);

    private final Properties properties;

    public StreamPropertiesProvider(ModulePropertiesStreamProvider streamProvider){
        this.properties = load(streamProvider);
    }

    private Properties load(ModulePropertiesStreamProvider streamProvider){
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
