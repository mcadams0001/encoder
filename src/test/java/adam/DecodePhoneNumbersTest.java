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
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;
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
    void setUp() {
        initMocks(this);
        decodePhoneNumbers = new DecodePhoneNumbers(mockFileHelper, mockControllerService);
    }

    @Test
    void processFiles() {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true);
        when(mockControllerService.decodeAndPrintPhoneNumbers(anyString(), anyString())).thenReturn(true);
        decodePhoneNumbers.processFiles("file1", "file2");
        InOrder inOrder = Mockito.inOrder(mockFileHelper, mockControllerService);
        inOrder.verify(mockFileHelper, times(2)).isCorrectFile(anyString());
        inOrder.verify(mockControllerService).decodeAndPrintPhoneNumbers("file1", "file2");
    }

    @Test
    void processInvalidFiles() {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(false);
        decodePhoneNumbers.processFiles("file1", "file2");
        verify(mockFileHelper).isCorrectFile(anyString());
        verify(mockControllerService, never()).decodeAndPrintPhoneNumbers("file1", "file2");
    }

    @Test
    void invalidFiles() {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertFalse(result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper, times(2)).isCorrectFile(captor.capture());
        List<String> allValues = captor.getAllValues();
        assertTrue(allValues.contains("file1"));
        assertTrue(allValues.contains("file2"));
    }


    @Test
    void invalidFilesSecondInvalid() {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true).thenReturn(false);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertTrue(result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper, times(2)).isCorrectFile(captor.capture());
        List<String> allValues = captor.getAllValues();
        assertTrue(allValues.contains("file1"));
        assertTrue(allValues.contains("file2"));
    }


    @Test
    void invalidFilesFirstInvalid() {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(false);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertTrue(result);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper).isCorrectFile(captor.capture());
        assertEquals("file1", captor.getValue());
    }

    @Test
    void namePrintParameters() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        DecodePhoneNumbers.main(new String[]{"file1"});
        assertTrue(os.toString().startsWith("Usage: [dictionary file name] [phone numbers file name]"));
    }

    @Test
    void processActualFiles() throws FileNotFoundException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File dictionaryFile = TestFileHelper.getTestFile("./small_dict.txt");
        File phoneNumbersFile = TestFileHelper.getTestFile("./small_sample.txt");
        DecodePhoneNumbers.main(new String[]{dictionaryFile.getAbsolutePath(), phoneNumbersFile.getAbsolutePath()});
        assertNotNull(os.toString());
    }
}