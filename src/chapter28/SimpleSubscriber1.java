package chapter28;

public class SimpleSubscriber1 {

    @Subscribe
    public void mothod1(String message){
        int i=1/0;
        System.out.println("SimpleSubscriber1=========mothod1======="+message);
    }

    @Subscribe(topic = "test")
    public void mothod2(String message){
        System.out.println("SimpleSubscriber1=========mothod2======="+message);
    }

}
