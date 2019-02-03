package adam.services;

import adam.services.helper.TestFileHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DictionaryServiceImplTest {
    private DictionaryServiceImpl service = new DictionaryServiceImpl();

    @Mock
    private BufferedReader mockBufferedReader;

    @Mock
    private Stream<String> mockStream;

    @Test
    void shouldCreateDictionaryFile() throws Exception {
        File dictionaryFile = TestFileHelper.getTestFile("./dictionary.txt");
        Map<String, Object> dictionaryMap = service.readAndCreateMap(dictionaryFile.getAbsolutePath());
        assertNotNull(dictionaryMap);
        assertEquals(72042, dictionaryMap.size());
        String[] arr = (String[]) dictionaryMap.get("556901");
        List<String> list = Arrays.asList(arr);
        assertTrue(list.contains("Aachen"));
        assertTrue(list.contains("machen"));
        assertEquals("aba\"ndern", dictionaryMap.get("57513021"));
    }

    @Test
    void shouldEncodeWordToNumber() {
        String encodedNumber = DictionaryServiceImpl.encodeWordToNumber("Aachen");
        assertEquals("556901", encodedNumber);
    }

    @Test
    void throwFileNotFoundException() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doThrow(new FileNotFoundException()).when(spyService).getBufferedReader(anyString());
        assertThrows(FileNotFoundException.class, () -> spyService.readAndCreateMap("test.txt"));
    }

    @Test
    void shouldReturnNullResource() {
        assertThrows(IOException.class, () -> service.readAndCreateMap(""));
    }

    @Test
    void shouldReturnNull() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doReturn(mockBufferedReader).when(spyService).getBufferedReader(anyString());
        when(mockBufferedReader.lines()).thenReturn(mockStream);
        doThrow(new UncheckedIOException("failure", new IOException())).when(spyService).createDictionaryMapNumberToWord(mockStream);
        assertThrows(UncheckedIOException.class, () -> spyService.readAndCreateMap("test"));

    }

    @Test
    void name() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doReturn(mockBufferedReader).when(spyService).getBufferedReader(anyString());
        doThrow(new IOException("failure")).when(mockBufferedReader).close();
        assertThrows(IOException.class, () -> spyService.readAndCreateMap("test"));

    }
}
