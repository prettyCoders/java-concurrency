package chapter28.directory_target_monitor;

import chapter28.EventBus;

import java.io.IOException;
import java.nio.file.*;

/**
 *DirectoryTargetMonitor 在创建 WatchService 之后将文件的修改、删除、创建等注册给了 WatchService,
 * 在指定目录下发生诸如此类的事件之后便会收到通知，将事件类型和发生变化的文件 Path 封装成 FileChangeEvent 提交给 EventBus
 */
public class DirectoryTargetMonitor {

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    private volatile boolean start=false;

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus,targetPath,"");
    }

    /**
     * 构造 Monitor 的时候需要传入 EventBus 以及需要监控的目录
     * @param eventBus EventBus
     * @param targetPath 目标路径
     * @param morePaths 监控的目录
     */
    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String ...morePaths){
        this.eventBus=eventBus;
        this.path= Paths.get(targetPath,morePaths);
    }

    public void startMonitor() throws IOException {
        this.watchService=FileSystems.getDefault().newWatchService();
        //为路径注册感兴趣的事件
        this.path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE
        );
        System.out.printf("The directory [%s] is monitoring...\n",path);
        this.start=true;
        while (start){
          WatchKey watchKey=null;
          try {
              //当有事件发生时会返回对应的 WatchKey
              watchKey=watchService.take();
              watchKey.pollEvents().forEach(watchEvent -> {
                  WatchEvent.Kind<?> kind=watchEvent.kind();
                  Path path= (Path) watchEvent.context();
                  Path child=DirectoryTargetMonitor.this.path.resolve(path);
                  //提交 FileChangeEvent 到 EventBus
                  eventBus.post(new FileChangeEvent(child,kind));
              });
          }catch (Exception e){
              this.start=false;
          }finally {
              if(watchKey!=null){
                  watchKey.reset();
              }
          }
        }
    }

    public void stopMonitor() throws IOException {
        System.out.printf("The directory [%s] monitor will be stop...\n",path);
        Thread.currentThread().interrupt();
        this.start=false;
        this.watchService.close();
        System.out.printf("The directory [%s] monitor will be stop done.\n",path);
    }

}
