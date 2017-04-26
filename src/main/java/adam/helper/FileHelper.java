package adam.helper;

import java.io.File;

/**
 * Helper class for file operations.
 */
public final class FileHelper {
    private static final String FILE_NOT_EXISTS = "The given file: %s doesn't exist";
    private static final String IS_NOT_A_FILE = "The given file: %s is not a file";
    private static final String CANT_READ = "The given file: %s cannot be read";

    private FileHelper() {
        //Do nothing.
    }

    public static boolean isCorrectFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            MyLogger.error(String.format(FILE_NOT_EXISTS, fileName));
            return false;
        }
        if (!file.isFile()) {
            MyLogger.error(String.format(IS_NOT_A_FILE, fileName));
            return false;
        }
        if (!file.canRead()) {
            MyLogger.error(String.format(CANT_READ, fileName));
            return false;
        }
        return true;
    }
}
