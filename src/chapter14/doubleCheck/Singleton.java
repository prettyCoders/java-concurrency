package chapter14.doubleCheck;

import java.net.Socket;
import java.sql.Connection;

/**
 * Double-Check
 * 不允许继承
 *
 * 满足多线程下单例、懒加载、高效获取实例
 */
public final class Singleton {
    //实例变量
    private byte[] bytes=new byte[1024];

    Connection connection;
    Socket socket;

    //在定义实例对象,但是不直接初始化，禁止JVM指令重排序（有可能第一个线程初始化了instance，但是还没来得及初始化类变量
    //第二个线程获取了刚才初始化完成的instance并调用 instance 的类变量如 connection 就会导致空指针
    private volatile static Singleton instance=null;

    //构造函数私有化，不允许外部new
    private Singleton(){
        //初始化类变量
    }
    public static  Singleton getInstance(){
        //当instance为null时，进入同步代码块，同时该判断避免了每次都进入同步代码块，提高了效率。
        if(null==instance){
            synchronized (Singleton.class){
                //该判断目的是避免多次初始化
                //场景：首次创建实例的时候，A进入同步代码块，B被synchronized，A创建完成释放monitor，B进入同步代码块
                //如果不加判断，则会初始化两次
                if(null==instance){
                    instance=new Singleton();
                }
            }
        }
        return instance;
    }
}
