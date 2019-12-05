package chapter28;

public class SimpleSubscriber2 {

    @Subscribe
    public void mothod1(String message){
        System.out.println("SimpleSubscriber2=========mothod1======="+message);
    }

    @Subscribe(topic = "test")
    public void mothod2(String message){
        System.out.println("SimpleSubscriber2=========mothod2======="+message);
    }

}
