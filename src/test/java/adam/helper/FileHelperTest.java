package adam.helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileHelperTest {

    private FileHelper fileHelper;

    @Mock
    private MyLogger mockMyLogger;

    @Mock
    private File mockFile;

    @Before
    public void setUp() throws Exception {
        fileHelper = new FileHelper(mockMyLogger);
    }

    @Test
    public void isCorrectFileNotExists() throws Exception {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile doesn't exist");
        assertThat(result, equalTo(false));
    }

    @Test
    public void isCorrectFileIsNotAFile() throws Exception {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile is not a file");
        assertThat(result, equalTo(false));
    }

    @Test
    public void isCorrectFileCannotBeRead() throws Exception {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(mockFile.canRead()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile cannot be read");
        assertThat(result, equalTo(false));
    }

}