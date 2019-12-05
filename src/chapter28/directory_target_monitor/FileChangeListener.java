package chapter28.directory_target_monitor;

import chapter28.Subscribe;

public class FileChangeListener {

    @Subscribe
    public void change(FileChangeEvent event) {
        System.out.printf("%s-%s\n", event.getPath(), event.getKind());
    }
}
