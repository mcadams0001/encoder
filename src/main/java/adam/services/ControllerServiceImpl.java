package adam.services;

import adam.helper.MyLogger;

import java.io.IOException;
import java.util.Map;

public class ControllerServiceImpl implements ControllerService {

    private DictionaryService dictionaryService;
    private MyLogger myLogger;

    public ControllerServiceImpl(DictionaryService dictionaryService) {

        this.dictionaryService = dictionaryService;
        this.myLogger = new MyLogger();
    }

    @Override
    public boolean decodeAndPrintPhoneNumbers(String dictionaryFileName, String phoneNumbersFileName) {
        Map<String, Object> dictionaryMap = getDirectoryMap(dictionaryFileName);
        return dictionaryMap != null && encodePhoneNumbersUsingDictionary(phoneNumbersFileName, dictionaryMap);
    }

    boolean encodePhoneNumbersUsingDictionary(String phoneNumbersFileName, Map<String, Object> dictionaryMap) {
        PhoneNumberService phoneNumberService = getPhoneNumberService(dictionaryMap);
        try {
            phoneNumberService.readAndEncodePhoneNumbers(phoneNumbersFileName);
        } catch (IOException e) {
            myLogger.error("Failed to process phone numbers file: " + phoneNumbersFileName + " with error message: " + e.getMessage(), e);
            return false;
        }
        return true;
    }

    PhoneNumberService getPhoneNumberService(Map<String, Object> dictionaryMap) {
        return new PhoneNumberServiceImpl(new EncodingServiceImpl(dictionaryMap));
    }

    Map<String, Object> getDirectoryMap(String dictionaryFileName) {
        try {
            return dictionaryService.readAndCreateMap(dictionaryFileName);
        } catch (IOException e) {
            myLogger.error("Failed to process dictionary file: " + dictionaryFileName + " with error message: " + e.getMessage(), e);
        }
        return null;
    }
}
