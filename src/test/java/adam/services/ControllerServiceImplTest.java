package adam.services;

import adam.helper.MyLogger;
import adam.services.helper.TestFileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


class ControllerServiceImplTest {

    @Mock
    private DictionaryService mockDictionaryService;

    @Mock
    private PhoneNumberService mockPhoneNumberService;

    @Mock
    private MyLogger mockLogger;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void testDecodeAndPrintPhoneNumbers() throws Exception {
        ControllerServiceImpl service = new ControllerServiceImpl(new DictionaryServiceImpl(), mockLogger);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File dictionaryFile = TestFileHelper.getTestFile("./small_dict.txt");
        File phoneNumbersFile = TestFileHelper.getTestFile("./small_sample.txt");
        boolean result = service.decodeAndPrintPhoneNumbers(dictionaryFile.getAbsolutePath(), phoneNumbersFile.getAbsolutePath());
        assertEquals(true, result);
        assertTrue(os.toString().contains("5624-82: Mix Tor"));
        assertTrue(os.toString().contains("5624-82: mir Tor"));
        assertTrue(os.toString().contains("10/783--5: neu o\"d 5"));
        assertTrue(os.toString().contains("10/783--5: je Bo\" da"));
        assertTrue(os.toString().contains("10/783--5: je bo\"s 5"));
    }

    @Test
    void testDecodeAndPrintPhoneNumbersAndReturnFalse() throws Exception {
        ControllerServiceImpl service = new ControllerServiceImpl(mockDictionaryService, mockLogger);
        ControllerServiceImpl spyService = spy(service);
        when(mockDictionaryService.readAndCreateMap(anyString())).thenReturn(new HashMap<>());
        doReturn(mockPhoneNumberService).when(spyService).getPhoneNumberService(anyMap());
        doThrow(new IOException()).when(mockPhoneNumberService).readAndEncodePhoneNumbers(anyString());
        boolean result = spyService.decodeAndPrintPhoneNumbers("dictFile", "numFile");
        assertEquals(false, result);
    }

    @Test
    void testReturnFalseOnEmptyDictionary() throws Exception {
        ControllerServiceImpl service = new ControllerServiceImpl(mockDictionaryService, mockLogger);
        boolean result = service.decodeAndPrintPhoneNumbers("dictFile", "numFile");
        assertEquals(false, result);
    }

    @Test
    void captureDirectoryMapExceptionAndReturnNull() throws Exception {
        ControllerServiceImpl service = new ControllerServiceImpl(mockDictionaryService, mockLogger);
        doThrow(new IOException("failed")).when(mockDictionaryService).readAndCreateMap(anyString());
        Map<String, Object> directoryMap = service.getDirectoryMap("small_dict.txt");
        verify(mockDictionaryService).readAndCreateMap(anyString());
        assertNull(directoryMap);
    }

    @Test
    void encodePhoneNumbersUsingDictionaryAndCaptureException() throws Exception {
        Map<String, Object> dictionaryMap = new HashMap<>();
        ControllerServiceImpl service = new ControllerServiceImpl(mockDictionaryService, mockLogger);
        ControllerServiceImpl spyService = spy(service);
        doReturn(mockPhoneNumberService).when(spyService).getPhoneNumberService(anyMap());
        doThrow(new IOException("failure")).when(mockPhoneNumberService).readAndEncodePhoneNumbers(anyString());
        boolean result = spyService.encodePhoneNumbersUsingDictionary("small_dict.txt", dictionaryMap);
        verify(mockPhoneNumberService).readAndEncodePhoneNumbers(anyString());
        assertEquals(false, result);
    }


}