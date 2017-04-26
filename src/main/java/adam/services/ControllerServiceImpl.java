package adam.services;

import adam.helper.MyLogger;

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
            MyLogger.error("Failed to process dictionary file: " + dictionaryFileName + " with error message: " + e.getMessage(), e);
            return false;
        }
        PhoneNumberService phoneNumberService = new PhoneNumberServiceImpl(dictionaryMap);
        try {
            phoneNumberService.readAndEncodePhoneNumbers(phoneNumbersFileName);
        } catch (IOException e) {
            MyLogger.error("Failed to process phone numbers file: " + phoneNumbersFileName + " with error message: " + e.getMessage(), e);
            return false;
        }
        return true;
    }
}
