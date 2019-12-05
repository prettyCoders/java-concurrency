package chapter28.directory_target_monitor;

import chapter28.AsyncEventBus;
import chapter28.EventBus;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorTest {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        final EventBus bus = new AsyncEventBus(executor);
        //注册
        bus.register(new FileChangeListener());
        DirectoryTargetMonitor monitor = new DirectoryTargetMonitor(bus, "D:\\studyProject\\java-concurrency\\src\\chapter28\\derectory_target_monitor");
        monitor.startMonitor();
    }

}
