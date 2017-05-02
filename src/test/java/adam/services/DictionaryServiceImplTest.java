package adam.services;

import adam.services.helper.TestFileHelper;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class DictionaryServiceImplTest {
    private DictionaryServiceImpl service = new DictionaryServiceImpl();

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

    @Test(expected = IOException.class)
    public void throwFileNotFoundException() throws Exception {
        DictionaryServiceImpl spyService = spy(service);
        doThrow(new FileNotFoundException()).when(spyService).getBufferedReader(anyString());
        spyService.readAndCreateMap("test.txt");
    }

}
