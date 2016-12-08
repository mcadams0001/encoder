package adam.services;

import adam.services.helper.TestFileHelper;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ControllerServiceImplTest {

    ControllerService controllerService = new ControllerServiceImpl();

    @Test
    public void testDecodeAndPrintPhoneNumbers() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File dictionaryFile = TestFileHelper.getTestFile("./small_dict.txt");
        File phoneNumbersFile = TestFileHelper.getTestFile("./small_sample.txt");
        boolean result = controllerService.decodeAndPrintPhoneNumbers(dictionaryFile.getAbsolutePath(), phoneNumbersFile.getAbsolutePath());
        assertThat(result, equalTo(true));
        assertThat(os.toString(), containsString("5624-82: Mix Tor"));
        assertThat(os.toString(), containsString("5624-82: mir Tor"));
        assertThat(os.toString(), containsString("10/783--5: neu o\"d 5"));
        assertThat(os.toString(), containsString("10/783--5: je Bo\" da"));
        assertThat(os.toString(), containsString("10/783--5: je bo\"s 5"));
    }
}