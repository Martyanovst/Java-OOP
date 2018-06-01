package ThreadDispatcher;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadDispatcher {
    private static final ThreadDispatcher instance = new ThreadDispatcher();
    final ConcurrentHashMap<Integer, ThreadedTask> tasks;
    private final IIdGenerator idGenerator;
    private final ThreadMonitor monitor;

    public static ThreadDispatcher getInstance() {
        return instance;
    }

    public void Add(ThreadedTask task) {
        int id = idGenerator.GetNextId();
        tasks.put(id, task);
        monitor.notifyIfChanged();
        Thread thread = new Thread(() -> {
            task.run();
            tasks.remove(id);
            monitor.notifyIfChanged();
        });
        thread.start();
    }

    private ThreadDispatcher() {
        tasks = new ConcurrentHashMap<>();
        idGenerator = SimpleIdGenerator.getInstance();
        monitor = new ThreadMonitor("Thread_Monitoring.txt", tasks, 2000);
        Add(monitor);
    }
}
