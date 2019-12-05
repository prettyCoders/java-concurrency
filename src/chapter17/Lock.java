package chapter17;

/**
 * 读写分离锁接口
 */
public interface Lock {

    //获取显示锁，没有获得锁的线程将被阻塞
    void lock() throws InterruptedException;

    //释放锁，主要目的是减少 reader 或 writer 的数量
    void unlock();

}
