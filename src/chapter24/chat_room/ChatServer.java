package chapter24.chat_room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 聊天室服务端
 */
public class ChatServer {

    //服务端端口
    private final int port;

    //线程池
    private ExecutorService threadPool;

    //服务端 Socket
    private ServerSocket serverSocket;

    public ChatServer(int port) {
        this.port = port;
    }

    //默认13312端口
    public ChatServer() {
        this(13312);
    }

    public void startServer() throws IOException {
        //初始化线程池，核心线程数量2，最大线程数4，阻塞队列最大可加入1000个任务
        threadPool = new ThreadPoolExecutor(2, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000));
        this.serverSocket=new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        System.out.println("Chat server is started and listen at port: "+port);
        this.listen();
    }

    private void listen() throws IOException {
        for(;;){
            //accept() 是阻塞方法，当有新的连接进入时才会有返回，并且返回的是客户端的连接
            Socket client=serverSocket.accept();
            //将客户端连接作为一个 Request 封装成对应的 Handler，然后提交给线程池
            this.threadPool.execute(new ClientHandler(client));
        }
    }

}
