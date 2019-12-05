package chapter16;

public class FlightSecurityTest {

    //旅客
    static class Passengers extends Thread{

        //机场安检
        private final FlightSecurity flightSecurity;

        //身份证
        private final String idCard;

        //登机牌
        private final String boardingPass;

        public Passengers(FlightSecurity flightSecurity, String idCard, String boardingPass) {
            this.flightSecurity = flightSecurity;
            this.idCard = idCard;
            this.boardingPass = boardingPass;
        }

        @Override
        public void run() {
            while (true){
                //旅客不断通过安检
                flightSecurity.pass(boardingPass,idCard);
//                System.out.println(boardingPass+" : "+idCard);
            }
        }

        public static void main(String[] args){
            final FlightSecurity flightSecurity=new FlightSecurity();
            new Passengers(flightSecurity,"AF123456","A123456").start();
            new Passengers(flightSecurity,"BF123456","B123456").start();
            new Passengers(flightSecurity,"CF123456","C123456").start();
        }
    }
}
