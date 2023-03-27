package by.kihtenkoolga;

public class Response {

    private int responseValue;

    public Response(int responseValue) {
        this.responseValue = responseValue;
    }

    public int get() {
        return responseValue;
    }

    public void set(int requestValue) {
        this.responseValue = requestValue;
    }

}
