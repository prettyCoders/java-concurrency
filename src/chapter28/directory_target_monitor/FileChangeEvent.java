package chapter28.directory_target_monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * FileChangeEvent 就是对 WatchEvent.Kind 和 Path 的封装，一旦目录发生任何改变，
 * 都会提交 FileChangeEvent 事件
 */
public class FileChangeEvent {

    private final Path path;

    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path=path;
        this.kind=kind;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }

    public Path getPath() {
        return path;
    }
}
