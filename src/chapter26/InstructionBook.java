package chapter26;

/**
 * Worker-Thread 设计模式(流水线设计模式)
 *
 * InstructionBook 说明书
 * 在流水线上需要被加工的产品，create 作为一个模板方法，提供了加工产品的说明书
 * 其中经过流水线传送带的产品将通过 create() 进行加工，而  firstProcess() 和 secondProcess()
 * 则代表加工每个产品的步骤，这就是说明书的作用
 */
public abstract class InstructionBook {

    public final void create(){
        this.firstProcess();
        this.secondProcess();
    }

    protected abstract void firstProcess();
    protected abstract void secondProcess();
}
