package chapter17;

import static java.lang.Thread.currentThread;

/**
 * 读写锁测试
 */
public class ReadWriteLockTTest {

    private final static String TEXT = "thisistheexampleforreadwritlock";

    public static void main(String[] args) {
        //定义共享数据
        final ShareData shareData = new ShareData(50);

        //创建两个线程进行写操作
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int index = 0; index < TEXT.length(); index++) {
                    try {
                        char c = TEXT.charAt(index);
                        shareData.write(c);
                        System.out.println(currentThread() + " write " + c);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        //创建10个线程进行读操作
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    try {
                        System.out.println(currentThread()+" read "+new String(shareData.read()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
