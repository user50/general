package general.http;

import org.apache.http.impl.client.HttpClients;

public class HttpServiceProvider {

    public HttpService get()
    {
        return new HttpService(HttpClients.custom().build());
    }
}
