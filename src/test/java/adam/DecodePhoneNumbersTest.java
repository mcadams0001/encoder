package adam;

import adam.helper.FileHelper;
import adam.services.ControllerService;
import adam.services.helper.TestFileHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DecodePhoneNumbersTest {

    private DecodePhoneNumbers decodePhoneNumbers;

    @Mock
    private ControllerService mockControllerService;
    @Mock
    private FileHelper mockFileHelper;

    @Before
    public void setUp() throws Exception {
        decodePhoneNumbers = new DecodePhoneNumbers(mockFileHelper, mockControllerService);
    }

    @Test
    public void processFiles() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true);
        when(mockControllerService.decodeAndPrintPhoneNumbers(anyString(), anyString())).thenReturn(true);
        decodePhoneNumbers.processFiles("file1", "file2");
        InOrder inOrder = Mockito.inOrder(mockFileHelper, mockControllerService);
        inOrder.verify(mockFileHelper, times(2)).isCorrectFile(anyString());
        inOrder.verify(mockControllerService).decodeAndPrintPhoneNumbers("file1", "file2");
    }

    @Test
    public void processInvalidFiles() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(false);
        decodePhoneNumbers.processFiles("file1", "file2");
        verify(mockFileHelper).isCorrectFile(anyString());
        verify(mockControllerService, never()).decodeAndPrintPhoneNumbers("file1", "file2");
    }

    @Test
    public void invalidFiles() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertThat(result, equalTo(false));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper, times(2)).isCorrectFile(captor.capture());
        List<String> allValues = captor.getAllValues();
        assertThat(allValues, hasItems("file1", "file2"));
    }


    @Test
    public void invalidFilesSecondInvalid() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(true).thenReturn(false);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertThat(result, equalTo(true));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper, times(2)).isCorrectFile(captor.capture());
        List<String> allValues = captor.getAllValues();
        assertThat(allValues, hasItems("file1", "file2"));
    }


    @Test
    public void invalidFilesFirstInvalid() throws Exception {
        when(mockFileHelper.isCorrectFile(anyString())).thenReturn(false);
        boolean result = decodePhoneNumbers.invalidFiles("file1", "file2");
        assertThat(result, equalTo(true));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockFileHelper).isCorrectFile(captor.capture());
        assertThat(captor.getValue(), equalTo("file1"));
    }

    @Test
    public void namePrintParameters() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        DecodePhoneNumbers.main(new String[]{"file1"});
        assertThat(os.toString(), equalTo("Usage: [dictionary file name] [phone numbers file name]\n"));
    }

    @Test
    public void processActualFiles() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        File dictionaryFile = TestFileHelper.getTestFile("./small_dict.txt");
        File phoneNumbersFile = TestFileHelper.getTestFile("./small_sample.txt");
        DecodePhoneNumbers.main(new String[]{dictionaryFile.getAbsolutePath(), phoneNumbersFile.getAbsolutePath()});
        assertThat(os.toString(), notNullValue());
    }
}