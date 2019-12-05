package chapter22;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 自动保存文档的线程
 */
public class AutoSaveThread extends Thread {

    private final Document document;

    public AutoSaveThread(Document document){
        super("DocumentAutoSaveThread");
        this.document=document;
    }

    @Override
    public void run() {
        while (true){
            try {
                //每隔一秒保存一次文档
                document.save();
                TimeUnit.SECONDS.sleep(1);
            } catch (IOException | InterruptedException e) {
                break;
            }
        }
    }
}
