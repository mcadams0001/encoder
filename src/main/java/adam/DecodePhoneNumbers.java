package adam;

import adam.helper.MyLogger;
import adam.services.ControllerService;
import adam.services.ControllerServiceImpl;
import adam.services.DictionaryService;
import adam.services.DictionaryServiceImpl;

import static adam.helper.FileHelper.isCorrectFile;

public class DecodePhoneNumbers {
    private DecodePhoneNumbers() {
        //Do nothing
    }
    public static void main(String[] args) {
        if (args.length < 2) {
            MyLogger.info("Usage: [dictionary file name] [phone numbers file name]");
            return;
        }
        String dictionaryFileName = args[0];
        String phoneNumberFileName = args[1];
        if(!isCorrectFile(dictionaryFileName) || !isCorrectFile(phoneNumberFileName)) {
            return;
        }
        DictionaryService dictionaryService = new DictionaryServiceImpl();
        ControllerService controllerService = new ControllerServiceImpl(dictionaryService);
        controllerService.decodeAndPrintPhoneNumbers(dictionaryFileName, phoneNumberFileName);
    }
}
