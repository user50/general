package general.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class JSONResponseHandler<T> implements ResponseHandler<T> {
    private final Logger logger = Logger.getLogger(JSONResponseHandler.class.getName() + ":" + hashCode());

    private static final String EMPTY_STRING = "";
    private static final String EMPTY_ARRAY = "[]";
    private static final String EMPTY_MAP = "{}";

    public static final ObjectMapper DEFAULT_MAPPER =  new ObjectMapper()
                            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    protected ObjectMapper mapper;

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T parse(String jsonString, JavaType javaType){
        if (jsonString == null || javaType == null ||
            jsonString.equals(EMPTY_STRING) || jsonString.equals(EMPTY_ARRAY) ||
            jsonString.equals(EMPTY_MAP))
        {
            return null;
        }

        try {
            return (T) (mapper != null ? mapper.readValue(jsonString, javaType)
                                : DEFAULT_MAPPER.readValue(jsonString, javaType));
        } catch (IOException e) {
            logger.severe("Can not parse jsonString " + jsonString + " to " +
                    javaType.toString() +"\n" + e.getMessage());
        }
        return null;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        String jsonString = new BasicResponseHandler().handleResponse(response);

        return parse(jsonString, constructJavaType());
    }

    protected abstract JavaType constructJavaType();
}
