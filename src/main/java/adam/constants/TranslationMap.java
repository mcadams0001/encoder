package adam.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Map representing a translation from dictionary characters to numbers.
 * The encoding is performed using the following:
 *
 * E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
 * e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
 * 0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9
 */
public final class TranslationMap {
    private TranslationMap() {
        //Do nothing
    }
    private static final Map<Character, Character> TRANSLATION_MAP = new HashMap<>();


    static {
        TRANSLATION_MAP.put('a', '5');
        TRANSLATION_MAP.put('b', '7');
        TRANSLATION_MAP.put('c', '6');
        TRANSLATION_MAP.put('d', '3');
        TRANSLATION_MAP.put('e', '0');
        TRANSLATION_MAP.put('f', '4');
        TRANSLATION_MAP.put('g', '9');
        TRANSLATION_MAP.put('h', '9');
        TRANSLATION_MAP.put('i', '6');
        TRANSLATION_MAP.put('j', '1');
        TRANSLATION_MAP.put('k', '7');
        TRANSLATION_MAP.put('l', '8');
        TRANSLATION_MAP.put('m', '5');
        TRANSLATION_MAP.put('n', '1');
        TRANSLATION_MAP.put('o', '8');
        TRANSLATION_MAP.put('p', '8');
        TRANSLATION_MAP.put('q', '1');
        TRANSLATION_MAP.put('r', '2');
        TRANSLATION_MAP.put('s', '3');
        TRANSLATION_MAP.put('t', '4');
        TRANSLATION_MAP.put('u', '7');
        TRANSLATION_MAP.put('v', '6');
        TRANSLATION_MAP.put('w', '2');
        TRANSLATION_MAP.put('x', '2');
        TRANSLATION_MAP.put('y', '3');
        TRANSLATION_MAP.put('z', '9');
    }

    public static Map<Character, Character> getTranslationMap() {
        return Collections.unmodifiableMap(TRANSLATION_MAP);
    }
}
