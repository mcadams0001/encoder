package adam.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileHelperTest {

    private FileHelper fileHelper;

    @Mock
    private MyLogger mockMyLogger;

    @Mock
    private File mockFile;

    @BeforeEach
    void setUp() {
        fileHelper = new FileHelper(mockMyLogger);
    }

    @Test
    void isCorrectFileNotExists() {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile doesn't exist");
        assertFalse(result);
    }

    @Test
    void isCorrectFileIsNotAFile() {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile is not a file");
        assertFalse(result);
    }

    @Test
    void isCorrectFileCannotBeRead() {
        FileHelper spyHelper = spy(fileHelper);
        doReturn(mockFile).when(spyHelper).getFile(anyString());
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isFile()).thenReturn(true);
        when(mockFile.canRead()).thenReturn(false);
        boolean result = spyHelper.isCorrectFile("someFile");
        verify(mockMyLogger).error("The given file: someFile cannot be read");
        assertFalse(result);
    }

}