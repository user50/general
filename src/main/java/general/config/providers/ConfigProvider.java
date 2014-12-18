package general.config.providers;

public interface ConfigProvider {

    /**
     * Returns property value by given property name.
     * Can throw ModulePropertyNotFoundException in case if property by given name is absent.
     * Should never return {@code null} or empty string values, in such case assume that property is absent.
     *
     * @throws ConfigNotFoundException
     * @return property by given {@code name}
     */
    public String getProperty(String name) throws ConfigNotFoundException;
}
