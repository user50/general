package general.elastic;

import org.elasticsearch.action.search.SearchResponse;

public interface ElasticsearchResponseHandler<T> {

    T handle(SearchResponse response);

}
