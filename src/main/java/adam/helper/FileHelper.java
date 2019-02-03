package adam.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Helper class for file operations.
 */
public class FileHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    public boolean isCorrectFile(String fileName) {
        File file = getFile(fileName);
        if (!file.exists()) {
            LOGGER.error("The given file: {} doesn't exist", fileName);
            return false;
        }
        if (!file.isFile()) {
            LOGGER.error("The given fileName: {} is not a file", fileName);
            return false;
        }
        if (!file.canRead()) {
            LOGGER.error("The given file: {} cannot be read", fileName);
            return false;
        }
        return true;
    }

    File getFile(String fileName) {
        return new File(fileName);
    }
}
