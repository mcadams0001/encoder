package adam.services.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.fail;

public class TestFileHelper {
    public static File getTestFile(String fileName) throws URISyntaxException, FileNotFoundException {
        URL resource = getTestFileUrl(fileName);
        if (resource == null) {
            throw new FileNotFoundException("Unable to find file:" + fileName);
        }
        return new File(new URI(resource.toString()));
    }

    public static URL getTestFileUrl(String fileName) {
        TestFileHelper test = new TestFileHelper();
        return test.getClass().getClassLoader().getResource(fileName);
    }
}
