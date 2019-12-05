package chapter03;

import java.util.stream.IntStream;

/**
 * 线程礼让，表示当前线程愿意放弃当前拥有的CPU资源
 * 调用yield方法会是当前线程从 RUNNING 状态切换到 RUNNABLE 状态
 * demo注释部分不释放的话，始终是0,1，释放了就有可能是1,0，具有不确定性
 */
public class ThreadYield {

    public static void main(String[] args) {
        IntStream.range(0, 2).mapToObj(ThreadYield::create).forEach(Thread::start);
    }

    private static Thread create(int index) {
        return new Thread(() -> {

//            if(index==0){
//                Thread.yield();
//            }

            System.out.println(index);
        });
    }
}


