package general.messaging;

import java.util.function.Consumer;

public interface MessageConsumer<T extends Message> extends Consumer<T> {
}
