package adam.services;

import java.util.List;

public interface EncodingService {
    /**
     * Encodes phone number to a readable word from dictionary.
     *
     * @param phoneNumber the phone number which might be any number holding characters like / or -
     * @return list with all possible encoded representation of a number. It is set of words which might hold one digit in between like - 1 Word Number
     */
    List<String> encodeNumber(String phoneNumber);
}
