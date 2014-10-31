package general.http;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

public class ProxyHttpClient {
    private final Logger logger = Logger.getLogger(ProxyHttpClient.class.getName() + ":" + hashCode());

    private CloseableHttpClient httpClient;
    private HttpHost httpHost;

    public ProxyHttpClient() {
    }

    public ProxyHttpClient(HttpHost httpHost, HttpClientConnectionManager connectionManager) {
        this.httpHost = httpHost;
        httpClient = connectionManager != null ? HttpClients.custom().setConnectionManager(connectionManager).build()
                                               : HttpClients.custom().build();
    }

    public <T> T doRequest(HttpRequest request, ResponseHandler<T> responseHandler){
        T response = null;
        try {
            response = httpClient.execute(httpHost, request, responseHandler);
        } catch (IOException e) {
            logger.severe("Request is failed : "
                    + request.getRequestLine().getUri());
        }

        return response;
    }

    public int doRequestAndReturnResponseCode(HttpRequest request){
        int result = -1;

        CloseableHttpResponse response = doRequest(request);

        if (response != null){
            result = response.getStatusLine().getStatusCode();
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                logger.severe("Can not consume the body content! Request: "
                        + request.getRequestLine().getUri());
            }
        }

        return result;
    }

    public CloseableHttpResponse doRequest(HttpRequest request) {
        CloseableHttpResponse response = null;

        try {
            logger.info("Proxy request: "
                    + request.getRequestLine().getUri());

            response = httpClient.execute(httpHost, request);

            logger.info("Proxy response: " + response);

        } catch (IOException e) {
            logger.severe("Request is failed : "
                    + request.getRequestLine().getUri());
        }

        return response;
    }


    public void destroy() {
        if (httpClient != null){
            try {
                httpClient.close();
                logger.info("Http client is closed successfully..");
            } catch (IOException e) {
                logger.severe("Can not close http client!");
            }
        }
    }
}
