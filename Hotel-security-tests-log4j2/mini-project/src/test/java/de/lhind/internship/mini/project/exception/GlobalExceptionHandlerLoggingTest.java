package de.lhind.internship.mini.project.exception;

import de.lhind.internship.mini.project.util.LogTestHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerLoggingTest {

    private static final String LOGGER_NAME = "de.lhind.internship.mini.project.exception.GlobalExceptionHandler";

    @Test
    void unexpectedException_logsFatal() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/hotels");
        when(request.getMethod()).thenReturn("GET");

        List<LogEvent> events = LogTestHelper.capture(LOGGER_NAME, Level.TRACE,
                () -> handler.handleGeneric(new RuntimeException("boom"), request));

        assertTrue(events.stream().anyMatch(e -> e.getLevel() == Level.FATAL));
    }
}
