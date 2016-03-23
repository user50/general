package general.elastic;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;

public class ElasticsearchService {

    Client client;

    public ElasticsearchService(Client client) {
        this.client = client;
    }

    public <T> T executeRequest(String[] indexes, ElasticsearchQueryProvider queryProvider, ElasticsearchResponseHandler<T> handler)
    {
        SearchResponse searchResponse = client.prepareSearch(indexes).setQuery(queryProvider.get()).setSize(50).execute().actionGet();

        return handler.handle(searchResponse);
    }

}
