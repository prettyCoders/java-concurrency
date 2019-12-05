package chapter14.eager;
/**
 * 饿汉式
 * 不允许继承
 *
 * 总结：可以保证多线程唯一实例，getInstance性能比较高，但是无法进行懒加载，instance被ClassLoader加载后
 * 可能很长一段时间才被使用，那就意味着instance实例所开辟的堆内存会驻留更久的时间，如果一个类的成员属性比较少
 * 饿汉的方式也未尝不可，但是如果类中的成员都是比较重的资源，那么就可以考虑换种方式
 */
public final class Singleton {
    //实例变量
    private byte[] data=new byte[1024];
    //在定义实例对象的时候直接初始化
    public static Singleton instance=new Singleton();
    //构造函数私有化，不允许外部new
    private Singleton(){
    }
    public Singleton getInstance(){
        return instance;
    }
}
