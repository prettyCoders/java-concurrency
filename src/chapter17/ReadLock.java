package chapter17;

/**
 * ReadLock 是 LocK 的实现，同样将其设置为包可见以隐藏其实现细节，让使用者只专注于对接口的调用
 */
class ReadLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        //使用 MUTEX 作为锁
        synchronized (readWriteLock.getMUTEX()) {
            //若此时有线程正在进行写操作，或者有写线程正在等待并且偏好于写，就挂起读锁
            while (readWriteLock.getWritingWriters() > 0 ||
                    (readWriteLock.getWaitingWriters() > 0 && readWriteLock.getPreferWriter())){
                readWriteLock.getMUTEX().wait();
            }
            //成功获得锁，并增加 readingReaders 的数量
            readWriteLock.incrementReadingReaders();
        }
    }

    @Override
    public void unlock() {
        //使用MUTEX作为锁
        synchronized (readWriteLock.getMUTEX()){
            //释放锁的过程就是减少 readingReader 的数量
            //将 preferWriter 设置为 true，可以使 writer 线程获得更多的机会
            //唤醒与 MUTEX关联的 monitor waitSet中的线程
            readWriteLock.decrementReadingReaders();
            readWriteLock.changePreferWriter(true);
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
