package general.messaging;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {

    private Map<MessageConsumer, ExecutorService> executors = new HashMap<>();
    private Map<Class<? extends Message>, Set<MessageConsumer>> messageConsumers = new HashMap<>();

    public <T extends  Message> void send(T message)
    {
        if (!messageConsumers.containsKey(message.getClass()))
        {
            System.out.println("Unable to find handler for message: "+message.getClass());

            return;
        }

        for (MessageConsumer messageConsumer : messageConsumers.get(message.getClass()))
            executors.get(messageConsumer).execute(() -> messageConsumer.accept(message));

    }

    public <T extends Message> void  register(MessageConsumer<T> consumer, Class<T> messageType, ExecutorService executorService)
    {
        executors.put(consumer, executorService);

        register(consumer, messageType);
    }


    public <T extends Message> void  register(MessageConsumer<T> consumer, Class<T> messageType, int threadCount)
    {
        if (threadCount>1)
            executors.put(consumer, Executors.newFixedThreadPool(threadCount));

        register(consumer, messageType);
    }

    public <T extends Message> void  register(MessageConsumer<T> consumer, Class<T> messageType)
    {
        if (!executors.containsKey(consumer))
            executors.put(consumer, Executors.newSingleThreadExecutor());

        if (!messageConsumers.containsKey(messageType))
        {
            Set<MessageConsumer> consumers = new HashSet<>();
            consumers.add(consumer);
            messageConsumers.put(messageType, consumers);

            return;
        }

        messageConsumers.get(messageType).add(consumer);
    }

}
