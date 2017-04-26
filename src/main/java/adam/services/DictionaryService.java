package adam.services;

import java.io.IOException;
import java.util.Map;

/**
 * Service providing functionality for processing a dictionary file.
 */
public interface DictionaryService {
    /**
     * Reads the dictionary text file.
     * Expected format is single word per line.
     * @param fileName the name of the dictionary file including it's location.
     * @return Map with key representing a translated value of a word into a number representation as specified at @link adam.constants.TranslationMap.
     *          The map can either contain one string or list of strings.
     * @throws IOException if the given files is missing or cannot be read.
     */
    Map<String, Object> readAndCreateMap(String fileName) throws IOException;
}
