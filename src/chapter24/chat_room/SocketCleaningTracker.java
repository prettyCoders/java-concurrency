package chapter24.chat_room;


import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.net.Socket;

/**
 * Socket 资源释放跟踪器
 */
class SocketCleaningTracker {
    //定义 ReferenceQueue
    private static final ReferenceQueue<Object> QUEUE = new ReferenceQueue<>();

    static {
        //启动 Cleaner 线程
        new Cleaner().start();
    }

    protected static void track(Socket socket) {
        new Tracker(socket, QUEUE);
    }

    private static class Cleaner extends Thread {

        private Cleaner(){
            super("SocketCleaningTracker");
            //清理动作一般是系统的清理工作，用于防止 JVM 无法正常关闭，
            setDaemon(true);
        }

        @Override
        public void run() {
            for (;;){
                try {
                    Tracker tracker= (Tracker) QUEUE.remove();
                    tracker.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Tracker 是 PhantomReference 的子类
     */
    private static class Tracker extends PhantomReference<Object>{

        private final Socket socket;

        Tracker(Socket socket,ReferenceQueue<? super Object> queue){
            super(socket,queue);
            this.socket=socket;
        }

        private void close(){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}