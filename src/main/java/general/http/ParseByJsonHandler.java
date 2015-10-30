package general.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public class ParseByJsonHandler<T> implements HttpResponseHandler<T> {

    Class<T> tClass;

    public ParseByJsonHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T handle(CloseableHttpResponse httpResponse) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(httpResponse.getEntity().getContent(), tClass );
    }
}
