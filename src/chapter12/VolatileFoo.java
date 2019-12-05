package chapter12;

import java.util.concurrent.TimeUnit;

/**
 * volatile关键字的原理和使用
 * volatile的原理和硬件CPU、Java内存模型（JMM）相关
 *
 * volatile只能修饰类变量和实例变量
 * 不保证原子性
 * 保证可见性
 * 保证有序性
 *
 * 应用场景：状态开关（如线程关闭操作或者叫结束死循环线程的执行）
 *
 */
public class VolatileFoo {
    //initValue最大值
    final static int MAX = 5;
    //initValue初始值
    static volatile int initValue = 0;

    public static void main(String[] args) {
        //启动一个Reader线程，当发现localValue与initValue不一致时，则输出initValue被修改的信息
        new Thread(() -> {
            int localValue = initValue;
            while (localValue < MAX) {
                if (initValue != localValue) {
                    System.out.printf("The initValue be update to [%d]\n", initValue);
                    //对localValue重新赋值
                    localValue = initValue;
                }
            }
        }, "Reader").start();


        //启动一个Updater线程，用于修改initValue，当localValue>=5的时候退出生命周期
        new Thread(() -> {
            int localValue = initValue;
            while (localValue < MAX) {
                System.out.printf("1 The initValue will be change to [%d]\n", ++localValue);
                initValue = localValue;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Updater").start();

        //测试volatile不保证原子性
//        new Thread(() -> {
//            int localValue = initValue;
//            while (localValue < MAX) {
//                System.out.printf("2 The initValue will be change to [%d]\n", ++localValue);
//                initValue = localValue;
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "Updater2").start();
    }


}
