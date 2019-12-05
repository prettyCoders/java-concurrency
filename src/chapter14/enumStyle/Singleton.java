package chapter14.enumStyle;

//枚举类型本身就是final的，不允许被继承
public class Singleton {

    //实例变量
    private byte[] bytes = new byte[1024];

    private Singleton() {
    }

    //使用枚举充当holder
    private enum EnumHolder {
        INSTANCE;
        private Singleton instance;

        EnumHolder() {
            this.instance = new Singleton();
        }

        private Singleton getSingleton() {
            return instance;
        }
    }


    public static Singleton getInstance() {
        return EnumHolder.INSTANCE.getSingleton();
    }
}
