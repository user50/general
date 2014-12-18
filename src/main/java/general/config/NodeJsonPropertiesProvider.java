package general.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class NodeJsonPropertiesProvider extends JsonPropertiesProvider {

    private final Map<String, Object> propertiesMap;

    public NodeJsonPropertiesProvider(String fileName, String nodeKey) {
        super(fileName);
        propertiesMap = (Map<String, Object>) getPropertiesMap().get(nodeKey);
    }

    public NodeJsonPropertiesProvider(String fileName, String nodeKey, ObjectMapper objectMapper) {
        super(fileName, objectMapper);
        propertiesMap = (Map<String, Object>) getPropertiesMap().get(nodeKey);
    }

    @Override
    protected Object getPropertyValue(String name){
        checkPropertyExists(name, propertiesMap == null);
        return propertiesMap.get(name);
    }
}
