package ThreadDispatcher;


public class Program {
    public static void main(String[] args) throws InterruptedException {
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
        dispatcher.Add(new SleepWorker());
        Thread.sleep(1000);
        dispatcher.Add(new SleepWorker());
        Thread.sleep(500);
        dispatcher.Add(new SleepWorker());
        dispatcher.Add(new SleepWorker());
        Thread.sleep(5000);
    }
}
