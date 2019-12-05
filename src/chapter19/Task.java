package chapter19;

public interface Task<IN,OUT> {

    //给定一个参数，根据计算返回结果
    OUT get(IN input);

}
