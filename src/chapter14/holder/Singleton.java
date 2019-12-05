package chapter14.holder;

/**
 * holder单例（最好的设计之一）
 * 不允许继承
 *
 * 在 Singleton 中并没有 instance 的静态成员，而是将其放到了静态内部类Holder中，
 * 因此在 Singleton 初始化的时候并不会创建 Singleton 的实例，Holder中定义了 Singleton 的静态变量，
 * 并且直接进行实例化，Singleton 实例的创建过程在Java程序编译时期被收集至<clinit>()方法中，并且该方法是同步方法，
 * 同步方法可以保证内存的可见性、JVN指令的顺序性、原子性
 */
public final class Singleton {
    //实例变量
    private byte[] bytes=new byte[1024];
    //构造函数私有化，不允许外部new
    private Singleton(){
    }
    //在静态内部类中持有Singleton的实例，并且可被直接初始化
    private static class Holder{
       private static Singleton instance=new Singleton();
    }
    //调用getInstance()，实际上是获取Holder的静态属性instance
    public static  Singleton getInstance(){
        return Holder.instance;
    }
}
