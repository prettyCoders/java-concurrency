package chapter24.chat_room;


import java.io.*;
import java.lang.ref.ReferenceQueue;
import java.net.Socket;
import java.util.Queue;

/**
 * ClientHandler 是一个 Runnable 接口的实现
 */
public class ClientHandler implements Runnable {

    //客户端的 Socket 连接
    private final Socket socket;

    //客户端的 identity
    private final String clientIdentity;

    public ClientHandler(final Socket socket) {
        this.socket = socket;
        this.clientIdentity = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        try {
            this.chat();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //任务执行结束时，执行释放资源的工作
            this.release();
        }
    }

    private void release() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            //关闭失败时，将 socket 实例加入 Tracker 中，进行跟踪，
            // 当JVM 对socket进行回收时，可尝试再次进行资源释放，提高资源关闭成功的概率
            SocketCleaningTracker.track(socket);
        }
    }

    private void chat() throws IOException {
        BufferedReader bufferedReader = wrap2Reader(this.socket.getInputStream());
        PrintStream printStream = wrap2Print(this.socket.getOutputStream());
        String received;
        while ((received = bufferedReader.readLine()) != null) {
            //将客户端发送的消息打印到控制台
            System.out.printf("client:%s-message:%s\n", clientIdentity, received);
            if (received.equals("quit")) {
                write2Client(printStream, "client will close.");
                socket.close();
                break;
            }
            //向客户端发送消息
            write2Client(printStream, "server:" + received);
        }
    }

    //该方法主要用于向客户端发送消息
    private void write2Client(PrintStream printStream, String message) {
        printStream.println(message);
        printStream.flush();
    }

    //将输出字节流封装成 PrintStream
    private PrintStream wrap2Print(OutputStream outputStream) {
        return new PrintStream(outputStream);
    }

    //将输入的字节流封装成 BufferedReader 缓冲字符流
    private BufferedReader wrap2Reader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
