package chapter15;

import java.util.concurrent.TimeUnit;

public class ObservableThreadTest {

    public static void main(String[] args) {
//        Observable observable=new ObservableThread<>(new Task<Object>() {
//            @Override
//            public Object call() {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(" finished done.");
//                return null;
//            }
//        });
//
//        observable.start();


        final TaskLifecycle<String> lifecycle = new TaskLifecycle.EmptyLifeCycle<String>() {
            //重写
            @Override
            public void onStart(Thread thread) {
                System.out.println("The thread is started");
            }

            @Override
            public void onRunning(Thread thread) {
                System.out.println("The thread is running");
            }

            @Override
            public void onFinish(Thread thread, String result) {
                System.out.println("The result is " + result);
            }

            @Override
            public void onError(Thread thread, Exception e) {
                System.out.println("The thread occur exception");
            }
        };

        Observable observable = new ObservableThread<>(lifecycle, new Task<String>() {
            @Override
            public String call() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" finished done.");
                return "Hello Observer";
            }
        });

        observable.start();
    }
}
