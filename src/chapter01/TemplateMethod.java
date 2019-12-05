package chapter01;

/**
 * 模板方法模式：父类编写算法结构代码，子类实现逻辑细节
 */
public class TemplateMethod {
    public final void print(String message){
        System.out.println("===========");
        warpPrint(message);
        System.out.println("===========");
    }

    protected void warpPrint(String message) {

    }

    public static void main(String[] args){
        new TemplateMethod(){
            @Override
            protected void warpPrint(String message) {
                System.out.println("+"+message+"+");
            }
        }.print("hello world");

        new TemplateMethod(){
            @Override
            protected void warpPrint(String message) {
                System.out.println("*"+message+"*");
            }
        }.print("hello world");
    }
}
