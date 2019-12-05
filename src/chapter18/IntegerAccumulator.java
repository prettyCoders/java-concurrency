package chapter18;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 不可变对象,每次更改都会返回新的对象
 * 脱离锁的依赖，不需要任何同步控制
 */
public final class IntegerAccumulator {

    private final int init;

    //构造时传入初始值
    public IntegerAccumulator(int init) {
        this.init = init;
    }

    //构造新的累加器，需要用到另一个累加器和初始值
    public IntegerAccumulator(IntegerAccumulator integerAccumulator2, int init) {
        this.init = integerAccumulator2.getValue() + init;
    }

    //每次相加都会产生新的累加器
    public IntegerAccumulator add(int i) {
        return new IntegerAccumulator(this, i);
    }

    //返回当前的初始值
    public int getValue() {
        return this.init;
    }

    public static void main(String[] args) {
        //定义累加器，并设置初始值为0
        IntegerAccumulator integerAccumulator = new IntegerAccumulator(0);
        //定义三个线程，并分别启动
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                //首先获得oldValue
                int oldValue = integerAccumulator.getValue();
                //然后调用add方法计算
                int result = integerAccumulator.add(inc).getValue();
                System.out.println(oldValue + "+" + inc + "=" + result);
                //验证，如果不合理，则输出错误信息
                if (inc + oldValue != result) {
                    System.out.println("ERROR:" + oldValue + "+" + inc + "=" + result);
                }
                inc++;
                slowly();
            }
        }).start());
    }

    private static void slowly() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
