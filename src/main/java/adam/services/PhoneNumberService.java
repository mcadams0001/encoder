package adam.services;

import java.io.IOException;

/**
 * Service used for processing a phone number file.
 */
public interface PhoneNumberService {
    /**
     * Processes a given phone number file and outputs the result of processing in System output.
     *
     * @param phoneNumberFileName the name of the phone number file including it's location.
     * @throws IOException thrown if file was not found or could not be read.
     */
    void readAndEncodePhoneNumbers(String phoneNumberFileName) throws IOException;
}
