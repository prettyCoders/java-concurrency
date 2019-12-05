package chapter06;

public class ThreadGroupCreator {

    public static void main(String[] args){
        //获取当前线程
        ThreadGroup currentGroup=Thread.currentThread().getThreadGroup();
        //定义一个新的group1
        ThreadGroup group1=new ThreadGroup("group1");
        System.out.println(group1.getParent()==currentGroup);

        //定义一个新的group2 ,指定他的父group为group1
        ThreadGroup group2=new ThreadGroup(group1,"group2");
        System.out.println(group1==group2.getParent());
    }
}
