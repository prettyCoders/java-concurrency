package chapter16;

/**
 * 模拟非线程安全的机场安检，要达到线程安全，需要为pass()加上同步控制关键字 synchronized
 */
public class FlightSecurity {

    private int count = 0;

    //登机牌
    private String boardingPass = "null";

    //身份证
    private String idCard = "null";

    public  void pass(String boardingPass, String idCard) {
        this.boardingPass = boardingPass;
        this.idCard = idCard;
        this.count++;
        this.check();
    }

    private void check() {
        //简单测试，当登机牌和身份证首字符不相同时则表示检查不通过
        if (boardingPass.charAt(0) != idCard.charAt(0)) {
            throw new RuntimeException("=====Exception====" + toString());
        }
    }

    public String toString() {
        return "The " + count + " Passengers,boardingPass [" + boardingPass + "],idCard [" + idCard + "]";
    }

}
