package adam.helper;

import java.io.File;

/**
 * Helper class for file operations.
 */
public final class FileHelper {
    public static boolean isCorrectFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("The given file :" + fileName + " doesn't exist");
            return false;
        }
        if (!file.isFile()) {
            System.err.printf("The given file :" + fileName + " is not a file");
            return false;
        }
        if (!file.canRead()) {
            System.err.printf("The given file :" + fileName + " cannot be read");
            return false;
        }
        return true;
    }
}
