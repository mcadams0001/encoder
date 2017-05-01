package adam.helper;

public class MyLogger {
    public void info(String message) {
        System.out.println(message);
    }

    public void error(String message) {
        System.err.println(message);
    }

    public void error(String message, Exception ex) {
        System.err.println(message);
        ex.printStackTrace(System.err);
    }
}
