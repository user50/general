package general.config;


/**
 * General interface which provide provide possibility get {@link com.dph.analytics.module.properties.ModuleProperties}
 * from different sources and strategies.
 *
 * @see com.dph.analytics.module.properties.ModuleProperties
 * @see ModulePropertyNotFoundException
 */
public interface PropertiesProvider {

    /**
     * Returns property value by given property name.
     * Can throw ModulePropertyNotFoundException in case if property by given name is absent.
     * Should never return {@code null} or empty string values, in such case assume that property is absent.
     *
     * @throws ModulePropertyNotFoundException
     * @return property by given {@code name}
     */
    public String getProperty(String name) throws ModulePropertyNotFoundException;
}
