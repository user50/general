package general.messaging;

public class TimeSpentMessageConsumer<T extends Message> implements MessageConsumer<T> {

    MessageConsumer<T> messageConsumer;
    String desc;

    public TimeSpentMessageConsumer(MessageConsumer<T> messageConsumer, String desc) {
        this.messageConsumer = messageConsumer;
        this.desc = desc;
    }

    @Override
    public void accept(T message) {

        long start = System.currentTimeMillis();
        messageConsumer.accept(message);
        System.out.println(desc + " spent " +(System.currentTimeMillis() - start) +" ms" );
    }
}
