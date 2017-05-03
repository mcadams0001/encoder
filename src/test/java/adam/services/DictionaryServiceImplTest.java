package adam.services;

import adam.services.helper.TestFileHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DictionaryServiceImplTest {
    private DictionaryServiceImpl service = new DictionaryServiceImpl();

    @Mock
    private BufferedReader mockBufferedReader;

    @Mock
    private Stream<String> mockStream;

    @Test
    public void shouldCreateDictionaryFile() throws Exception {
        File dictionaryFile = TestFileHelper.getTestFile("./dictionary.txt");
        Map<String,Object> dictionaryMap = service.readAndCreateMap(dictionaryFile.getAbsolutePath());
        assertThat(dictionaryMap, notNullValue());
        assertThat(dictionaryMap.size(), equalTo(72042));
        assertThat((String[])dictionaryMap.get("556901"), hasItemInArray("Aachen"));
        assertThat((String[]) dictionaryMap.get("556901"), hasItemInArray("machen"));
        assertThat(dictionaryMap.get("57513021"), equalTo("aba\"ndern"));
    }

    @Test
    public void shouldEncodeWordToNumber() throws Exception {
        String encodedNumber = DictionaryServiceImpl.encodeWordToNumber("Aachen");
        assertThat(encodedNumber, equalTo("556901"));
    }

    @Test(expected = FileNotFoundException.class)
    public void throwFileNotFoundException() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doThrow(new FileNotFoundException()).when(spyService).getBufferedReader(anyString());
        spyService.readAndCreateMap("test.txt");
    }

    @Test(expected = IOException.class)
    public void shouldReturnNullResource() throws Exception {
        service.readAndCreateMap("");
    }

    @Test(expected = UncheckedIOException.class)
    public void shouldReturnNull() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doReturn(mockBufferedReader).when(spyService).getBufferedReader(anyString());
        when(mockBufferedReader.lines()).thenReturn(mockStream);
        doThrow(new UncheckedIOException("failure", new IOException())).when(spyService).createDictionaryMapNumberToWord(mockStream);
        spyService.readAndCreateMap("test");
    }

    @Test(expected = IOException.class)
    public void name() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doReturn(mockBufferedReader).when(spyService).getBufferedReader(anyString());
        doThrow(new IOException("failure")).when(mockBufferedReader).close();
        spyService.readAndCreateMap("test");

    }
}
