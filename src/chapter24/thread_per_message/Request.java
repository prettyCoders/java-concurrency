package chapter24.thread_per_message;

/**
 * 客户端提交的任何业务受理请求都会被封装成 Request 对象
 */
public class Request {

    private final String business;

    public Request(String business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return business;
    }
}
