package chapter06;

import java.util.concurrent.TimeUnit;

/**
 * 用法与特性 enumerate(Thread[] list) 和 enumerate(Thread[] list,boolean recurse)相同
 */
public class ThreadGroupEnumerateThreadGroups {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        ThreadGroup group2 = new ThreadGroup(group1, "group2");

        TimeUnit.MILLISECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        ThreadGroup[] list = new ThreadGroup[mainGroup.activeGroupCount()];

        int groupCount = mainGroup.enumerate(list);
        System.out.println(groupCount);

        groupCount = mainGroup.enumerate(list,false);
        System.out.println(groupCount);

    }
}
