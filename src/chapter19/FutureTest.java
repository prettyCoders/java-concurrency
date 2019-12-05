package chapter19;

import java.util.concurrent.TimeUnit;

/**
 * Future 测试
 */
public class FutureTest {
    public static void main(String[] args) throws InterruptedException {

        //无返回结果
//        FutureService<Void, Void> futureService = FutureService.newService();
//        Future<?> future = futureService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("I an finish done.");
//            }
//        });
//        //任务未完成时，get()会进入阻塞状态
//        future.get();


//        //有返回结果,不使用回调
//        FutureService<String,Integer> futureService=FutureService.newService();
//        Future<Integer> future =futureService.submit(new Task<String, Integer>() {
//            //自定义任务：计算字符串长度
//            @Override
//            public Integer get(String input) {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return input.length();
//            }
//        },"HELLO",null);
//        System.out.println(future.get());


        //有返回结果,使用回调，不需要主动调用 get() 进而造成阻塞
        FutureService<String,Integer> futureService=FutureService.newService();
        futureService.submit(new Task<String, Integer>() {
            //自定义任务：计算字符串长度
            @Override
            public Integer get(String input) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return input.length();
            }
        }, "HELLO",new resultHandler());
    }

    static class resultHandler implements CallBack<Integer>{

        @Override
        public void call(Integer result) {
            //进一步处理线程执行后的结果，这里简单打印下
            System.out.println(result);
        }
    }
}
