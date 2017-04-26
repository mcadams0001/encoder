package adam.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;


public class PhoneNumberServiceImpl implements PhoneNumberService {

    private EncodingService encodingService;


    public PhoneNumberServiceImpl(Map<String, Object> dictionaryMap) {
        this.encodingService = new EncodingServiceImpl(dictionaryMap);
    }

    @Override
    public void readAndEncodePhoneNumbers(String phoneNumberFileName) throws IOException {
        try (BufferedReader buf = new BufferedReader(new FileReader(phoneNumberFileName))) {
            convertAndPrintLines(buf.lines(), System.out);
        }
    }

    /**
     * Converts the read stream of lines from phone number file.
     * @param lineStream stream with lines from the phone number file.
     * @param printStream print stream where the information is going to be printed.
     */
    void convertAndPrintLines(Stream<String> lineStream, PrintStream printStream) {
        lineStream.filter(Objects::nonNull).forEach(l -> printPhoneNumbers(l, printStream));
    }

    /**
     * Prints phone numbers in a format [phone number]: [encoded string].
     * @param phoneNumber the phone number to be printed.
     * @param printStream the print stream used for printing the information.
     */
    void printPhoneNumbers(String phoneNumber, PrintStream printStream) {
        List<String> encodedNumberList = encodingService.encodeNumber(removeSpecialCharacters(phoneNumber));
        encodedNumberList.forEach(encodedNumber -> printStream.println(phoneNumber + ": " + encodedNumber.trim()));
    }

    /**
     * Removes special characters like - or / from the phone numbers file.
     * @param str string representing phone number.
     * @return phone number holding digits only.
     */
    static String removeSpecialCharacters(String str) {
        return str.trim().replaceAll("-", "").replaceAll("/", "");
    }


}

