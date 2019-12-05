package chapter26;

/**
 * Production 产品
 */
public class Production extends InstructionBook {

    //产品ID
    private final int prodID;

    public Production(int prodID) {
        this.prodID = prodID;
    }

    @Override
    protected void firstProcess() {
        System.out.println("execute the "+prodID+" first process");
    }

    @Override
    protected void secondProcess() {
        System.out.println("execute the "+prodID+" second process");
    }

    @Override
    public String toString() {
        return String.valueOf(this.prodID);
    }
}
