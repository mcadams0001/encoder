package adam.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class FileHelperTest {

    private FileHelper fileHelper;

    @Mock
    private MyLogger mockMyLogger;

    @Mock
    private File mockFile;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        fileHelper = new FileHelper(mockMyLogger);
    }

    @Test
    void isCorrectFileNotExists() throws Exception {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile doesn't exist");
        assertEquals(false, result);
    }

    @Test
    void isCorrectFileIsNotAFile() throws Exception {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile is not a file");
        assertEquals(false, result);
    }

    @Test
    void isCorrectFileCannotBeRead() throws Exception {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(mockFile.canRead()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile cannot be read");
        assertEquals(false, result);
    }

}