package general.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticClientProvider {

    String clusterName;
    String host;
    int port;

    public ElasticClientProvider(String clusterName, String host, int port) {
        this.clusterName = clusterName;
        this.host = host;
        this.port = port;
    }

    public Client get()
    {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", clusterName).build();

        return new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port));
    }

}
