package chapter24.thread_per_message;

public class Test {

    public static void main(String[] args) {
        Operator operator=new Operator();
        for (int i = 0; i < 30; i++) {
            operator.call(String.valueOf(i));
        }
        operator.executorService.shutdown();
    }
}
