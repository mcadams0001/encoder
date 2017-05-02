package adam.helper;

import java.io.PrintStream;

public class MyLogger {

    private PrintStream infoPrintStream = System.out;
    private PrintStream errorPrintStream = System.err;

    public MyLogger() {
    }

    public MyLogger(PrintStream infoPrintStream, PrintStream errorPrintStream) {
        this.infoPrintStream = infoPrintStream;
        this.errorPrintStream = errorPrintStream;
    }

    public void info(String message) {

        infoPrintStream.println(message);
    }

    public void error(String message) {
        errorPrintStream.println(message);
    }

    public void error(String message, Exception ex) {
        errorPrintStream.println(message);
        ex.printStackTrace(errorPrintStream);
    }
}
