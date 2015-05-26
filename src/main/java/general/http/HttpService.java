package general.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user50 on 26.05.2015.
 */
public class HttpService {

    CloseableHttpClient httpClient;
    HttpHost host;

    public HttpService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <T> T execute(HttpRequestProvider httpRequestProvider, HttpResponseHandler<T> responseHandler) throws IOException {

        HttpRequestBase httpRequest = httpRequestProvider.getRequest();

        try (CloseableHttpResponse response = httpClient.execute(host, httpRequest)) {

            return responseHandler.handle(response);
        }
    }
}