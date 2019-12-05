package chapter22;

/**
 * Balking 设计模式测试
 */
public class BalkingTest {

    public static void main(String[] args){
        new DocumentEditThread("D:\\studyProject\\java-concurrency\\src\\chapter22","document.txt").start();
    }
}
