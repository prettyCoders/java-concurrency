package chapter07.preventDuplicated;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 防止程序重复启动，在进程启动时会创建一个lock文件，进程收到中断信号时会删除这个文件
 * 注意：Hook 线程对于类似 kill -9 这类强制杀死进程的方法无效
 */
public class PreventDuplicated {
    private static final String LOCK_PATH = "./locks";

    private static final String LOCK_FILE = ".lock";

    private static final String PERMISSIONS = "rw-------";

    public static void main(String[] args) throws IOException {
        //注入hook线程，在程序退出时删除lock文件
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("The program received kill SIGNAL");
            getLockFilePath().toFile().delete();
        }));

        //检查.lock文件是否存在
        checkRunning();

        //简单模拟当前程序正在运行
        for (; ; ) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                System.out.println("program is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //检查程序是否在运行中
    private static void checkRunning() throws IOException {
        Path path = getLockFilePath();
        if (path.toFile().exists()) {
            throw new RuntimeException("The program already running");
        }

        //创建文件夹和文件的方法根据系统而定，下面的方式适用于支持POSIX的操作系统，比如Ubuntu
        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(PERMISSIONS);
        Files.createFile(path, PosixFilePermissions.asFileAttribute(permissions));
    }

    //获取lock文件path
    private static Path getLockFilePath() {
        return Paths.get(LOCK_PATH, LOCK_FILE);
    }
}
