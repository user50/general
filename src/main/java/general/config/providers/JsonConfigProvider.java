package general.config.providers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class JsonConfigProvider extends AbstractConfigProvider {

    private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<Map<String,Object>>() {};
    private static final Logger LOGGER = Logger.getLogger(JsonConfigProvider.class);

    private final Map<String, Object> propertiesMap;

    public JsonConfigProvider(String fileName) {
        this(fileName, new ObjectMapper());
    }

    public JsonConfigProvider(String fileName, ObjectMapper objectMapper) {
        this.propertiesMap = loadProperties(fileName, objectMapper);
    }

    private Map<String, Object> loadProperties(String fileName, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(new File(fileName), TYPE_REFERENCE);
        } catch (IOException e) {
            LOGGER.error("Failed load properties from : " + fileName, e);
            return emptyMap();
        }
    }

    @Override
    protected Object getOptionalProperty(String name) {
        checkPropertyExists(name, propertiesMap == null);
        return getPropertyValue(name);
    }

    protected Object getPropertyValue(String name){
        return propertiesMap.get(name);
    }

    protected Map<String, Object> getPropertiesMap() {
        return propertiesMap;
    }
}
