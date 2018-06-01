package ThreadDispatcher;

class SleepWorker extends ThreadedTask {
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }
}
