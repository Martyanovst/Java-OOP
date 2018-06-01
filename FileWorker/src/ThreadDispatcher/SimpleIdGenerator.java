package ThreadDispatcher;

public class SimpleIdGenerator implements IIdGenerator {
    private static final SimpleIdGenerator instance = new SimpleIdGenerator();
    private volatile int currentID;

    public static SimpleIdGenerator getInstance() {
        return instance;
    }

    private SimpleIdGenerator() {
        currentID = 1;
    }

    @Override
    public synchronized int GetNextId() {
        return currentID++;
    }
}
