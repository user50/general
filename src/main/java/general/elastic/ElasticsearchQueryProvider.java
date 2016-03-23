package general.elastic;

import org.elasticsearch.index.query.QueryBuilder;

public interface ElasticsearchQueryProvider {

    QueryBuilder get();

}
