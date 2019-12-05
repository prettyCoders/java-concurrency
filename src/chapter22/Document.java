package chapter22;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;

/**
 * Balking 模式之文档编辑
 * Document 代表文档本身，有两个主要方法,save() 保存，edit() 编辑
 */
public class Document {

    //如果文档发生改变，changed 置为true
    private boolean changed = false;

    //一次需要保存的内容，可以将其理解为内容缓存
    private List<String> content = new ArrayList<>();

    private FileWriter fileWriter;

    //自动保存文档线程
    private static AutoSaveThread autoSaveThread;

    //构造函数需要传入文档保存路径和文档名
    private Document(String documentPath, String documentName) throws IOException {
        this.fileWriter = new FileWriter(new File(documentPath, documentName), true);
    }

    //静态方法，主要用于创建文档，顺便启动自动保存文档的线程
    public static Document create(String documentPath, String documentName) throws IOException {
        Document document = new Document(documentPath, documentName);
        autoSaveThread = new AutoSaveThread(document);
        autoSaveThread.start();
        return document;
    }

    //编辑就是往content队列中提交字符串
    public void edit(String content) {
        synchronized (this) {
            this.content.add(content);
            changed = true;
        }
    }

    //文档关闭时，首先中断自动保存线程，然后关闭 FileWriter 释放资源
    public void close() throws IOException {
        autoSaveThread.interrupt();
        fileWriter.close();
    }

    //save() 用于为外部显示进行文档保存
    public void save() throws IOException {
        synchronized (this) {
            //balking 如果文档已经保存，则直接返回
            if (!changed) return;
            System.out.println(currentThread() + " execute the save action.");
            //将内容写入文档
            for (String cacheLine : content) {
                fileWriter.write(cacheLine);
                fileWriter.write("\r\n");
            }
            fileWriter.flush();
            changed=false;
            content.clear();
        }
    }
}
