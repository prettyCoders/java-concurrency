package chapter05.booleanLock;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Lock接口，定义显示锁的相关接口
 */
public interface Lock {

    //和synchronized类似，不过该方法可以被中断，中断时会抛出InterruptedException
    void lock() throws InterruptedException;

    //处理可被中断外，还增加了对应的超时功能
    void lock(long mills) throws InterruptedException,TimeoutException;

    //释放锁
    void unlock();

    //获取当前哪些线程被阻塞
    List<Thread> getBlockedThreads();

}
