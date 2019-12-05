package chapter08;

/**
 * ThreadFactory 提供创建线程的接口，以便于个性化定制Thread，比如Thread应该放到哪个Group中、优先级、线程名、是否为守护线程等
 */
@FunctionalInterface
public interface ThreadFactory {

    Thread createThread(Runnable runnable);

}
