package general.config;

public abstract class AbstractPropertiesProvider implements PropertiesProvider {

    @Override
    public String getProperty(String name) throws ModulePropertyNotFoundException {
        return checkPropertyExists(name, getOptionalProperty(name));
    }

    protected abstract Object getOptionalProperty(String name);

    protected final String checkPropertyExists(String name, Object property) {
        checkPropertyExists(name, property == null);
        return property.toString();
    }

    protected final void checkPropertyExists(String name, boolean condition) {
        if(condition) {
            throw new ModulePropertyNotFoundException(name);
        }
    }
}
