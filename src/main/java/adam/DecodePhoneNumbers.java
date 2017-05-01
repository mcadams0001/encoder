package adam;

import adam.helper.FileHelper;
import adam.helper.MyLogger;
import adam.services.ControllerService;
import adam.services.ControllerServiceImpl;
import adam.services.DictionaryService;
import adam.services.DictionaryServiceImpl;

public class DecodePhoneNumbers {
    private DecodePhoneNumbers() {
        //Do nothing
    }
    public static void main(String[] args) {
        MyLogger myLogger = new MyLogger();
        if (args.length < 2) {
            myLogger.info("Usage: [dictionary file name] [phone numbers file name]");
            return;
        }
        String dictionaryFileName = args[0];
        String phoneNumberFileName = args[1];
        FileHelper fileHelper = new FileHelper(myLogger);
        if(!fileHelper.isCorrectFile(dictionaryFileName) || !fileHelper.isCorrectFile(phoneNumberFileName)) {
            return;
        }
        DictionaryService dictionaryService = new DictionaryServiceImpl();
        ControllerService controllerService = new ControllerServiceImpl(dictionaryService);
        controllerService.decodeAndPrintPhoneNumbers(dictionaryFileName, phoneNumberFileName);
    }
}
