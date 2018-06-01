package ThreadDispatcher;

import java.io.*;
import java.util.Map;

public class ThreadMonitor extends ThreadedTask {
    private final Map<Integer, ThreadedTask> threads;
    private final File file;
    private final Object locker = new Object();
    private final int timeout;
    private volatile boolean blocked;

    public ThreadMonitor(String file, Map<Integer, ThreadedTask> threads, int timeout) {
        this.threads = threads;
        this.file = new File(file);
        this.timeout = timeout;
    }

    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();
        while (true) {
            while (true) {
                synchronized (locker) {
                    if (!blocked) {
                        blocked = true;
                        break;
                    }
                    try {
                        locker.wait(timeout);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
            builder.setLength(0);
            for (Integer id : threads.keySet()) {
                builder.append(String.format("id: %d \t task: %s ", id, threads.get(id).getClass().toString()));
                builder.append(System.lineSeparator());
            }
            System.out.println(builder.toString());
            try (FileWriter out = new FileWriter(file)) {
                out.write(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void notifyIfChanged() {
        synchronized (locker) {
            blocked = false;
            locker.notify();
        }
    }
}
