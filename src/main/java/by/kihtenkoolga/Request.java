package by.kihtenkoolga;

public class Request {

    private int requestValue;

    public Request(int requestValue) {
        this.requestValue = requestValue;
    }

    public int get() {
        return requestValue;
    }

    public void set(int requestValue) {
        this.requestValue = requestValue;
    }

}
