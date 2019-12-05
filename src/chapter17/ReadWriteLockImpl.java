package chapter17;

/**
 * 相对于 Lock，ReadWriteLockImpl 更像是一个工厂类，可以通过它创建不同类型的锁，
 * 将 ReadWriteLockImpl 设计为包可见类，主要目的是不想对外暴露太多细节
 */
class ReadWriteLockImpl implements ReadWriteLock {

    //定义对象锁
    private final Object MUTEX = new Object();

    private int waitingWriters=0;

    private int writingWriters=0;

    private int readingReaders=0;

    //read和write的偏好设置
    private boolean preferWriter;

    //默认情况下偏好于writer

    public ReadWriteLockImpl() {
        this(true);
    }

    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    @Override
    public Lock readLock() {
        return new ReadLock(this);
    }

    @Override
    public Lock writeLock() {
        return new WriteLock(this);
    }

    @Override
    public int getWritingWriters() {
        return this.writingWriters;
    }

    @Override
    public int getWaitingWriters() {
        return this.waitingWriters;
    }

    @Override
    public int getReadingReaders() {
        return this.readingReaders;
    }

    //使写线程的数量增加
    void incrementWritingWriters(){
        this.writingWriters++;
    }

    //使等待写入线程的数量增加
    void incrementWaitingWriters(){
        this.waitingWriters++;
    }

    //使读线程的数量增加
    void incrementReadingReaders(){
        this.readingReaders++;
    }

    //使写线程的数量减少
    void decrementWritingWriters(){
        this.writingWriters--;
    }

    //使等待写入的线程的数量减少
    void decrementWaitingWriters(){
        this.waitingWriters--;
    }

    //使等待写入的线程的数量减少
    void decrementReadingReaders(){
        this.readingReaders--;
    }

    //获取对象锁
    Object getMUTEX(){
        return this.MUTEX;
    }

    //是否当前偏好于写锁
    boolean getPreferWriter(){
        return this.preferWriter;
    }

    //设置偏好
    void changePreferWriter(boolean preferWriter){
        this.preferWriter=preferWriter;
    }

}
