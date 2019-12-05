package chapter14.lazy;
/**
 * 懒汉式
 * 不允许继承
 *
 * 采用懒汉式+数据同步的方式既满足了懒加载，又能保证instance实例的唯一性，
 * 但是 synchronized 关键字天生的排他性导致 getInstance() 同一时刻只能被一个线程访问，性能低下
 */
public final class Singleton {
    //实例变量
    private byte[] bytes=new byte[1024];
    //在定义实例对象,但是不直接初始化
    public static Singleton instance=null;
    //构造函数私有化，不允许外部new
    private Singleton(){
    }
    //加入同步控制，多线程环境下保证实例的唯一性
    public static synchronized Singleton getInstance(){
        if(null==instance){
            instance=new Singleton();
        }
        return instance;
    }
}
