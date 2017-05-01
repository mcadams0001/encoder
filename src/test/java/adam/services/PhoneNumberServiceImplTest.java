package adam.services;

import adam.services.fixtures.DictionaryFixture;
import adam.services.helper.TestFileHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class PhoneNumberServiceImplTest {

    private PhoneNumberServiceImpl service;

    @Before
    public void setup() throws Exception {
        service = new PhoneNumberServiceImpl(new EncodingServiceImpl(DictionaryFixture.DICTIONARY_MAP));
    }

    @Test
    public void testReadAndEncodePhoneNumbers() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File testFile = TestFileHelper.getTestFile("./small_sample.txt");
        service.readAndEncodePhoneNumbers(testFile.getAbsolutePath());
        assertThat(os.toString(), containsString("5624-82: Mix Tor"));
        assertThat(os.toString(), containsString("5624-82: mir Tor"));
        assertThat(os.toString(), containsString("10/783--5: neu o\"d 5"));
        assertThat(os.toString(), containsString("10/783--5: je Bo\" da"));
        assertThat(os.toString(), containsString("10/783--5: je bo\"s 5"));
    }

    @Test
    public void testConvertAndPrintLines() throws Exception {
        Stream<String> stream = Stream.of("5624-82", "10/783--5");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        service.convertAndPrintLines(stream, printStream);
        assertThat(os.toString(), containsString("5624-82: Mix Tor"));
        assertThat(os.toString(), containsString("5624-82: mir Tor"));
        assertThat(os.toString(), containsString("10/783--5: neu o\"d 5"));
        assertThat(os.toString(), containsString("10/783--5: je Bo\" da"));
        assertThat(os.toString(), containsString("10/783--5: je bo\"s 5"));
    }

    @Test
    public void testPrintPhoneNumbers() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        service.printPhoneNumbers("5624-82", printStream);
        assertThat(os.toString(), containsString("5624-82: Mix Tor"));
        assertThat(os.toString(), containsString("5624-82: mir Tor"));
    }

    @Test
    public void testConvertString() throws Exception {
        assertThat(PhoneNumberServiceImpl.removeSpecialCharacters("10/783--5"), equalTo("107835"));
    }

    @Test(expected = IOException.class)
    public void rethrowIOException() throws Exception {
        PhoneNumberServiceImpl spyService = spy(service);
        doThrow(new FileNotFoundException("failure")).when(spyService).getBufferedReader(anyString());
        spyService.readAndEncodePhoneNumbers("someFile");
    }
}