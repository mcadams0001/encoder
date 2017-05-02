package adam.services;

import adam.constants.TranslationMap;
import adam.helper.MapValueListCollector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Service used for processing dictionary file.
 */
public class DictionaryServiceImpl implements DictionaryService {

    public Map<String,  Object> readAndCreateMap(String fileName) throws IOException {
        try (BufferedReader buf = getBufferedReader(fileName)) {
            return createDictionaryMapNumberToWord(buf.lines());
        }
    }

    BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }

    Map<String, Object> createDictionaryMapNumberToWord(Stream<String> lines) {
        return lines.filter(Objects::nonNull).map(String::trim).collect(new MapValueListCollector(DictionaryServiceImpl::encodeWordToNumber));
    }

    public static String encodeWordToNumber(String str) {
        Map<Character, Character> translationMap = TranslationMap.getTranslationMap();
        List<Character> chList = str.toLowerCase().chars().mapToObj(c -> translationMap.get((char) c)).collect(toList());
        return chList.stream().filter(Objects::nonNull).map(String::valueOf).collect(joining());
    }

}
