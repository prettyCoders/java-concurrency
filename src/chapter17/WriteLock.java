package chapter17;

/**
 * WriteLock 是 LocK 的实现，同样将其设置为包可见以隐藏其实现细节，让使用者只专注于对接口的调用
 */
class WriteLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;


    WriteLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }


    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMUTEX()) {
            try {
                //首先增加等待获取写入锁的线程数量
                readWriteLock.incrementWaitingWriters();
                //如果此时有其他线程正在写或者读，那么当前线程挂起
                while (readWriteLock.getReadingReaders() > 0 || readWriteLock.getWritingWriters() > 0) {
                    readWriteLock.getMUTEX().wait();
                }
            }finally {
                //成功获取到写入锁，减少等待获取写入锁的线程数量
                this.readWriteLock.decrementWaitingWriters();
            }
            //正在写入线程的数量增加
            this.readWriteLock.incrementWritingWriters();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMUTEX()){
            //减少正在写入的线程数量
            readWriteLock.decrementWritingWriters();
            //将偏好设置为false，可以使得读锁被最快速的获得
            readWriteLock.changePreferWriter(false);
            //唤醒与 MUTEX关联的 monitor waitSet中的线程
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
