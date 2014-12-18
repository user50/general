package general.config;

import general.config.providers.ConfigProvider;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

/**
 * Created by user50 on 18.12.2014.
 */
public abstract class Config {

    private ConfigProvider configProvider;

    public Config(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    protected String get(String name) {
        return configProvider.getProperty(name);
    }

    protected int getInteger(String name) {
        return parseInt(get(name));
    }

    protected long getLong(String name) {
        return parseLong(get(name));
    }

    protected float getFloat(String name) {
        return parseFloat(get(name));
    }

    protected double getDouble(String name) {
        return parseDouble(get(name));
    }

    protected boolean getBoolean(String name) {
        return parseBoolean(get(name));
    }
}
