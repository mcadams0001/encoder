package adam;

import adam.services.ControllerService;
import adam.services.ControllerServiceImpl;

import static adam.helper.FileHelper.isCorrectFile;

public class DecodePhoneNumbers {
    public static void main(String[] args) {
        if (args.length < 2) {
            printUsageMessage();
            return;
        }
        String dictionaryFileName = args[0];
        String phoneNumberFileName = args[1];
        if(!isCorrectFile(dictionaryFileName) || !isCorrectFile(phoneNumberFileName)) {
            return;
        }
        ControllerService controllerService = new ControllerServiceImpl();
        controllerService.decodeAndPrintPhoneNumbers(dictionaryFileName, phoneNumberFileName);
    }

    private static void printUsageMessage() {
        System.out.println("Usage: [dictionary file name] [phone numbers file name]");
    }


}
