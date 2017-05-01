package adam.services;

import adam.services.helper.TestFileHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ControllerServiceImplTest {

    @Mock
    private DictionaryService mockDictionaryService;

    @Mock
    private PhoneNumberService mockPhoneNumberService;

    @Test
    public void testDecodeAndPrintPhoneNumbers() throws Exception {
        ControllerServiceImpl service = new ControllerServiceImpl(new DictionaryServiceImpl());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File dictionaryFile = TestFileHelper.getTestFile("./small_dict.txt");
        File phoneNumbersFile = TestFileHelper.getTestFile("./small_sample.txt");
        boolean result = service.decodeAndPrintPhoneNumbers(dictionaryFile.getAbsolutePath(), phoneNumbersFile.getAbsolutePath());
        assertThat(result, equalTo(true));
        assertThat(os.toString(), containsString("5624-82: Mix Tor"));
        assertThat(os.toString(), containsString("5624-82: mir Tor"));
        assertThat(os.toString(), containsString("10/783--5: neu o\"d 5"));
        assertThat(os.toString(), containsString("10/783--5: je Bo\" da"));
        assertThat(os.toString(), containsString("10/783--5: je bo\"s 5"));
    }

    @Test
    public void captureDirectoryMapExceptionAndReturnNull() throws Exception {
        ControllerServiceImpl service = new ControllerServiceImpl(mockDictionaryService);
        doThrow(new IOException("failed")).when(mockDictionaryService).readAndCreateMap(anyString());
        Map<String, Object> directoryMap = service.getDirectoryMap("small_dict.txt");
        verify(mockDictionaryService).readAndCreateMap(anyString());
        assertThat(directoryMap, nullValue());
    }

    @Test
    public void encodePhoneNumbersUsingDictionaryAndCaptureException() throws Exception {
        Map<String, Object> dictionaryMap = new HashMap<>();
        ControllerServiceImpl service = new ControllerServiceImpl(mockDictionaryService);
        ControllerServiceImpl spyService = spy(service);
        doReturn(mockPhoneNumberService).when(spyService).getPhoneNumberService(anyMap());
        doThrow(new IOException("failure")).when(mockPhoneNumberService).readAndEncodePhoneNumbers(anyString());
        boolean result = spyService.encodePhoneNumbersUsingDictionary("small_dict.txt", dictionaryMap);
        verify(mockPhoneNumberService).readAndEncodePhoneNumbers(anyString());
        assertThat(result, equalTo(false));
    }
}