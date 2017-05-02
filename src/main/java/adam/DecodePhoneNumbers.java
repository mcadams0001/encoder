package adam;

import adam.helper.FileHelper;
import adam.helper.MyLogger;
import adam.services.ControllerService;
import adam.services.ControllerServiceImpl;
import adam.services.DictionaryServiceImpl;

public class DecodePhoneNumbers {

    private ControllerService controllerService;
    private FileHelper fileHelper;

    public DecodePhoneNumbers(FileHelper fileHelper, ControllerService controllerService) {
        this.fileHelper = fileHelper;
        this.controllerService = controllerService;
    }

    public static void main(String[] args) {
        MyLogger myLogger = new MyLogger();
        if (args.length < 2) {
            myLogger.info("Usage: [dictionary file name] [phone numbers file name]");
            return;
        }
        String dictionaryFileName = args[0];
        String phoneNumberFileName = args[1];
        DecodePhoneNumbers decodePhoneNumbers = new DecodePhoneNumbers(new FileHelper(myLogger), new ControllerServiceImpl(new DictionaryServiceImpl(), myLogger));
        decodePhoneNumbers.processFiles(dictionaryFileName, phoneNumberFileName);
    }

    void processFiles(String dictionaryFileName, String phoneNumberFileName) {
        if (invalidFiles(dictionaryFileName, phoneNumberFileName)) {
            return;
        }
        controllerService.decodeAndPrintPhoneNumbers(dictionaryFileName, phoneNumberFileName);

    }

    boolean invalidFiles(String dictionaryFileName, String phoneNumberFileName) {
        return !fileHelper.isCorrectFile(dictionaryFileName) || !fileHelper.isCorrectFile(phoneNumberFileName);
    }
}
