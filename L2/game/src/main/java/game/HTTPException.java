package game;

public class HTTPException extends Exception {
    int statusCode;

    public HTTPException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
