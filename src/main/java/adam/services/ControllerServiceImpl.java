package adam.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ControllerServiceImpl implements ControllerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerServiceImpl.class);
    private DictionaryService dictionaryService;

    public ControllerServiceImpl(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
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
            LOGGER.error("Failed to process phone numbers file: {} with error message: {}", phoneNumbersFileName, e.getMessage(), e);
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
            LOGGER.error("Failed to process dictionary file: {} with error message: {}", dictionaryFileName, e.getMessage(), e);
        }
        return null;
    }
}
