package adam.helper;

import java.io.File;

/**
 * Helper class for file operations.
 */
public class FileHelper {
    private static final String FILE_NOT_EXISTS = "The given file: %s doesn't exist";
    private static final String IS_NOT_A_FILE = "The given file: %s is not a file";
    private static final String CANT_READ = "The given file: %s cannot be read";
    private MyLogger myLogger;

    public FileHelper(MyLogger myLogger) {
        this.myLogger = myLogger;
    }

    public boolean isCorrectFile(String fileName) {
        File file = getFile(fileName);
        if (!file.exists()) {
            myLogger.error(String.format(FILE_NOT_EXISTS, fileName));
            return false;
        }
        if (!file.isFile()) {
            myLogger.error(String.format(IS_NOT_A_FILE, fileName));
            return false;
        }
        if (!file.canRead()) {
            myLogger.error(String.format(CANT_READ, fileName));
            return false;
        }
        return true;
    }

    File getFile(String fileName) {
        return new File(fileName);
    }
}
