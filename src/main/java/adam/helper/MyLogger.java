package adam.helper;

public final class MyLogger {
    public static void info(String message) {
        System.out.println(message);
    }

    public static void error(String message) {
        System.err.println(message);
    }

    public static void error(String message, Exception ex) {
        System.err.println(message);
        ex.printStackTrace(System.err);
    }
}
