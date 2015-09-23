package general.modules;

public class JdbcServiceModuleConfig {

    String url;
    int maxPoolSize;

    public JdbcServiceModuleConfig(String url, int maxPoolSize) {
        this.url = url;
        this.maxPoolSize = maxPoolSize;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
