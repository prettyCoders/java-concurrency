package chapter05.booleanLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {

    //当前拥有锁的线程
    private Thread currentThread;

    //false代表当前没有任何线程获取了锁
    //true代表当前锁已经被某个线程获取了，该线程就是currentThread
    private boolean locked = false;

    //存储哪些线程在获取锁时进入了阻塞状态
    private final List<Thread> blockedThreadList = new ArrayList<>();

    @Override
    public void lock() throws InterruptedException {
        //使用同步代码块的方式进行方法同步
        synchronized (this) {
            //如果当前锁已经被某个线程获得，则当前线程将加入阻塞队列，并且使当前线程wait,释放对this monitor的所有权
            while (locked) {
                //替换当前线程
                final Thread tempThread=Thread.currentThread();
                try {
                    if (!blockedThreadList.contains(tempThread)) {
                        blockedThreadList.add(tempThread);
                    }
                    this.wait();
                }catch (InterruptedException e){
                    //如果当前线程在wait时被中断，则从阻塞队列中移除，避免内存泄漏
                    blockedThreadList.remove(tempThread);
                    //继续抛出中断异常
                    throw e;
                }

            }
        }
        //如果当前锁没有被其他线程获得，则该线程尝试从阻塞队列中删除自己，
        //如果当前线程从未进入过阻塞队列，删除方法不会有任何影响；
        // 如果当前线程是从wait set中被唤醒的，则需要从阻塞队列中将自己删除
        blockedThreadList.remove(Thread.currentThread());
        //locked开关指定为true
        this.locked = true;
        //记录获取锁的线程
        this.currentThread = Thread.currentThread();
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            //如果mills不合法，调用默认的lock()，当然也可以抛出参数不合法异常
            if (mills < 0) {
                this.lock();
            } else {
                //距离超时的时间，也就是剩余时间
                long remainingMills = mills;
                //计算超时的时间点
                long endMills = System.currentTimeMillis() + remainingMills;
                //如果锁被占用，则将当前线程添加进阻塞队列，等待给定时间后尝试获取锁，根据重新计算后的剩余时间判断是否超时
                while (locked) {
                    //这个判断是根据后面重新计算后的remainingMills来判断的
                    //如果剩余时间少于0，则意味着当前线程被其他线程唤醒
                    //或者在指定的wait时间到了之后还没获取到锁，这种情况会抛出超时异常
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the block during " + mills+" ms.");
                    }
                    final Thread tempThread=Thread.currentThread();
                    try {
                        //将当前线程阻塞进阻塞队列
                        if (!blockedThreadList.contains(tempThread)) {
                            blockedThreadList.add(tempThread);
                        }
                        //等待给定的wait毫秒数，该值最开始由其他线程传入，但在多次wait的过程中会重新计算
                        this.wait(remainingMills);
                        //重新计算剩余时间
                        remainingMills = endMills - System.currentTimeMillis();
                    }catch (InterruptedException e){
                        blockedThreadList.remove(tempThread);
                        throw e;
                    }

                }
                //获取锁，并且从阻塞队列中删除当前线程，
                blockedThreadList.remove(Thread.currentThread());
                //locked开关指定为true
                this.locked = true;
                //记录获取锁的线程
                this.currentThread = Thread.currentThread();
            }
        }
    }


    @Override
    public void unlock() {
        synchronized (this) {
            //判断当前线程是否为拥有锁的线程
            if (this.currentThread == Thread.currentThread()) {
                //将锁的locked状态修改为false
                this.locked = false;
                Optional.of(Thread.currentThread().getName()+" release the lock.").ifPresent(System.out::println);
                //通知其他在wait set 中的线程，你们可以尝试抢锁了，这里使用notify()或者notifyAll()都可以
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedThreadList);
    }
}
