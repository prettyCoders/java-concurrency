package chapter22;

import java.io.IOException;
import java.util.Scanner;

/**
 * 该线程表示主动对文档编辑的线程，使用 Scanner 增加交互性
 */
public class DocumentEditThread extends Thread{

    private String documentPath;

    private String documentName;

    private Scanner scanner=new Scanner(System.in);

    public DocumentEditThread(String documentPath, String documentName) {
        super("DocumentEditThread");
        this.documentPath = documentPath;
        this.documentName = documentName;
    }

    @Override
    public void run() {
        int items=0;
        try {

            Document document=Document.create(documentPath,documentName);
            while (true){
                //获取用户键盘输入
                String text=scanner.next();
                if("quit".equals(text)){
                    document.close();
                    break;
                }
                //将内容编辑到document中
                document.edit(text);
                if(items==5){
                    //用户在输入5次之后进行文档保存
                    document.save();
                    items=0;
                }
                items++;
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
