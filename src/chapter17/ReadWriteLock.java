package chapter17;

/**
 * ReadWriteLock 虽然名字有 lock,但是它并不是 lock，它主要是用于创建 read lock 和 write lock 的，
 * 并且提供了查询功能用与查询当前有多少个reader 和 writer 以及 waiting 中的 writer
 */
public interface ReadWriteLock {

    //创建 reader 锁
    Lock readLock();

    //创建 writer 锁
    Lock writeLock();

    //获取当前有多少个线程在进行写操作，最多是1个
    int getWritingWriters();

    //获取当前有多少个线程正在等待获取写入锁
    int getWaitingWriters();

    //获取当前有多少个线程正在进行读操作
    int getReadingReaders();

    //工厂方法，创建ReadWriteLock
    static ReadWriteLock readWriteLock(){
        return new ReadWriteLockImpl();
    }

    //工厂方法，创建ReadWriteLock,并且传入 preferWriter
    static ReadWriteLock readWriteLock(boolean preferWriter){
        return new ReadWriteLockImpl(preferWriter);
    }

}
