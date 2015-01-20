package general.http;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * Created by user50 on 26.12.2014.
 */
public class UsageExample {
    CloseableHttpClient httpClient = HttpClients.custom().build();
    HttpHost targetHost = new HttpHost("postponed.depositphotos.com", 8085, "http");

    public void translate(Collection<String> phrases, String targetLang, String provider) throws URISyntaxException, IOException {
        URIBuilder queryRequestBuilder = new URIBuilder("/postponed-translate");

        for (String phrase : phrases)
            queryRequestBuilder.addParameter("phrase", phrase);

        queryRequestBuilder.addParameter("targetLang", targetLang);
        queryRequestBuilder.addParameter("sourceLang", targetLang);
        queryRequestBuilder.addParameter("provider", provider);

        HttpGet httpget = new HttpGet(queryRequestBuilder.build());

        try(CloseableHttpResponse response = httpClient.execute(targetHost, httpget))
        {
            String responseBody = EntityUtils.toString(response.getEntity());
        }
    }
}
