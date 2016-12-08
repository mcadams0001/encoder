package adam.services;

/**
 * Main invocation service.
 */
public interface ControllerService {
    /**
     * Reads dictionary file and phone number file.
     * For each entry in phone number file decodes the given number.
     * Each phone number is printed with equivalent text string with words found in the dictionary in format [phone number], [encoded words]
     * Multiple entries for the same phone number might be printed if they exist.
     * @param dictionaryFileName   - file name with location of the dictionary text file.
     * @param phoneNumbersFileName - file name with location of the phone number text file.
     * @return true if file processing was successful otherwise false;
     */
    boolean decodeAndPrintPhoneNumbers(String dictionaryFileName, String phoneNumbersFileName);
}
