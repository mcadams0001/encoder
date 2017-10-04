package adam;

import adam.helper.FileHelper;
import adam.services.ControllerService;
import adam.services.helper.TestFileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class DecodePhoneNumbersTest {

    private DecodePhoneNumbers decodePhoneNumbers;

    @Mock
    private ControllerService mockControllerService;
    @Mock
    private FileHelper mockFileHelper;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        decodePhoneNumbers = new DecodePhoneNumbers(mockFileHelper, mockControllerService);
    }

    @Test
    void processFiles() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true);
        when(mockControllerService.decodeAndPrintPhoneNumbers(anyString(), anyString())).thenReturn(true);
        decodePhoneNumbers.processFiles("file1", "file2");
        InOrder inOrder = Mockito.inOrder(mockFileHelper, mockControllerService);
        inOrder.verify(mockFileHelper, times(2)).isCorrectFile(anyString());
        inOrder.verify(mockControllerService).decodeAndPrintPhoneNumbers("file1", "file2");
    }

    @Test
    void processInvalidFiles() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(false);
        decodePhoneNumbers.processFiles("file1", "file2");
        verify(mockFileHelper).isCorrectFile(anyString());
        verify(mockControllerService, never()).decodeAndPrintPhoneNumbers("file1", "file2");
    }

    @Test
    void invalidFiles() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertEquals(false, result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper, times(2)).isCorrectFile(captor.capture());
        List<String> allValues = captor.getAllValues();
        assertTrue(allValues.contains("file1"));
        assertTrue(allValues.contains("file2"));
    }


    @Test
    void invalidFilesSecondInvalid() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true).thenReturn(false);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertEquals(true, result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper, times(2)).isCorrectFile(captor.capture());
        List<String> allValues = captor.getAllValues();
        assertTrue(allValues.contains("file1"));
        assertTrue(allValues.contains("file2"));
    }


    @Test
    void invalidFilesFirstInvalid() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(false);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertEquals(true, result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper).isCorrectFile(captor.capture());
        assertEquals("file1", captor.getValue());
    }

    @Test
    void namePrintParameters() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        DecodePhoneNumbers.main(new String[]{"file1"});
        assertTrue( os.toString().startsWith("Usage: [dictionary file name] [phone numbers file name]"));
    }

    @Test
    void processActualFiles() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File dictionaryFile = TestFileHelper.getTestFile("./small_dict.txt");
        File phoneNumbersFile = TestFileHelper.getTestFile("./small_sample.txt");
        DecodePhoneNumbers.main(new String[]{dictionaryFile.getAbsolutePath(), phoneNumbersFile.getAbsolutePath()});
        assertNotNull(os.toString());
    }
}