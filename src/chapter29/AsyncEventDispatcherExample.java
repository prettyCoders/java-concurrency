package chapter29;

import java.util.concurrent.TimeUnit;

public class AsyncEventDispatcherExample {

    /**
     * 主要用于处理 InputEvent,但是需要继承 AsyncChannel
     */
    static class AsyncImputEventHandler extends AsyncChannel{

        private final AsyncEventDispatcher dispatcher;

        public AsyncImputEventHandler(AsyncEventDispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }


        /**
         * 不同于以同步的方式实现 dispatch,异步的方式需要实现 Handle
         */
        @Override
        protected void handle(Event message) {
            EventDispatcherExample.InputEvent inputEvent= (EventDispatcherExample.InputEvent) message;
            System.out.printf("X:%d,Y:%d\n", inputEvent.getX(), inputEvent.getY());
            try {
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            int result=inputEvent.getX()+inputEvent.getY();
            dispatcher.dispatch(new EventDispatcherExample.ResultEvent(result));
        }
    }

    /**
     * 主要用于处理 InputEvent的 result ,但是需要继承 AsyncChannel
     */
    static class AsyncResultEventHandler extends AsyncChannel{

        @Override
        protected void handle(Event message) {
            EventDispatcherExample.ResultEvent resultEvent= (EventDispatcherExample.ResultEvent) message;
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The result is: "+resultEvent.getResult());
        }
    }

    public static void main(String[] args){
        AsyncEventDispatcher dispatcher=new AsyncEventDispatcher();
        dispatcher.registerChannel(EventDispatcherExample.InputEvent.class,new AsyncImputEventHandler(dispatcher));
        dispatcher.registerChannel(EventDispatcherExample.ResultEvent.class,new AsyncResultEventHandler());
        dispatcher.dispatch(new EventDispatcherExample.InputEvent(1,1));
        dispatcher.dispatch(new EventDispatcherExample.InputEvent(2,2));
        dispatcher.dispatch(new EventDispatcherExample.InputEvent(3,3));
        dispatcher.dispatch(new EventDispatcherExample.InputEvent(4,4));
    }
}
