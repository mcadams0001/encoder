package adam.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class MyLoggerTest {

    @Mock
    private PrintStream mockPrintStream;
    @Mock
    private PrintStream mockErrorStream;
    @Mock
    private Exception mockException;

    private MyLogger logger;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        logger = new MyLogger(mockPrintStream, mockErrorStream);
    }

    @Test
    void testInfo() throws Exception {
        new MyLogger();
        logger.info("test");
        verify(mockPrintStream).println("test");
    }

    @Test
    void testError() throws Exception {
        logger.error("test");
        verify(mockErrorStream).println("test");
    }

    @Test
    void testErrorWithException() throws Exception {
        logger.error("test", mockException);
        verify(mockErrorStream).println("test");
        verify(mockException).printStackTrace(mockErrorStream);
    }
}