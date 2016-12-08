package adam.services;

import adam.constants.TranslationMap;
import adam.helper.MapValueListCollector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Service used for processing dictionary file.
 */
public class DictionaryServiceImpl implements DictionaryService {

    public Map<String,  Object> readAndCreateMap(String fileName) throws IOException {
        try (BufferedReader buf = new BufferedReader(new FileReader(fileName))) {
            return createDictionaryMapNumberToWord(buf.lines());
        }
    }

    static Map<String, Object> createDictionaryMapNumberToWord(Stream<String> lines) {
        return lines.filter(l -> l != null).map(String::trim).collect(new MapValueListCollector(DictionaryServiceImpl::encodeWordToNumber));
    }

    static String encodeWordToNumber(String str) {
        List<Character> chList = str.toLowerCase().chars().mapToObj(c -> TranslationMap.TRANSLATION_MAP.get((char) c)).collect(toList());
        return chList.stream().filter(c -> c != null).map(String::valueOf).collect(joining());
    }

}
