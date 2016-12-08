package adam.services;

import java.io.IOException;
import java.util.Map;

public class ControllerServiceImpl implements ControllerService {

    @Override
    public boolean decodeAndPrintPhoneNumbers(String dictionaryFileName, String phoneNumbersFileName) {
        Map<String, Object> dictionaryMap;
        DictionaryService dictionaryService = new DictionaryServiceImpl();
        try {
            dictionaryMap = dictionaryService.readAndCreateMap(dictionaryFileName);
        } catch (IOException e) {
            System.err.println("Failed to process dictionary file: " + dictionaryFileName + " with error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl(dictionaryMap);
        try {
            phoneNumberService.readAndEncodePhoneNumbers(phoneNumbersFileName);
        } catch (IOException e) {
            System.err.println("Failed to process phone numbers file: " + phoneNumbersFileName + " with error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
