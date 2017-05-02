package adam.helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MyLoggerTest {

    @Mock
    private PrintStream mockPrintStream;
    @Mock
    private PrintStream mockErrorStream;
    @Mock
    private Exception mockException;

    private MyLogger logger;

    @Before
    public void setUp() throws Exception {
        logger = new MyLogger(mockPrintStream, mockErrorStream);
    }

    @Test
    public void testInfo() throws Exception {
        new MyLogger();
        logger.info("test");
        verify(mockPrintStream).println("test");
    }

    @Test
    public void testError() throws Exception {
        logger.error("test");
        verify(mockErrorStream).println("test");
    }

    @Test
    public void testErrorWithException() throws Exception {
        logger.error("test", mockException);
        verify(mockErrorStream).println("test");
        verify(mockException).printStackTrace(mockErrorStream);
    }
}