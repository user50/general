package general.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;

public class SimpleTypeJSONResponseHandler<T> extends JSONResponseHandler<T>{

    private final Class<T> type;

    public SimpleTypeJSONResponseHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    protected JavaType constructJavaType() {
        return SimpleType.construct(type);
    }
}
