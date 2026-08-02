package de.lhind.internship.mini.project.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Test helper that temporarily attaches an in-memory appender to a given
 * logger, runs some code, and returns exactly what was logged — without
 * touching the console/file appenders or leaving the logger's level changed
 * afterward. This is what lets tests check "did this log at this level?"
 * without eyeballing console output by hand.
 */
public class LogTestHelper {

    public static List<LogEvent> capture(String loggerName, Level level, Runnable action) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        LoggerConfig loggerConfig = context.getConfiguration().getLoggerConfig(loggerName);
        Level originalLevel = loggerConfig.getLevel();

        List<LogEvent> captured = new ArrayList<>();
        CapturingAppender appender = new CapturingAppender("CapturingAppender", captured);
        appender.start();

        loggerConfig.addAppender(appender, level, null);
        loggerConfig.setLevel(level);
        context.updateLoggers();

        try {
            action.run();
        } finally {
            loggerConfig.removeAppender("CapturingAppender");
            loggerConfig.setLevel(originalLevel);
            context.updateLoggers();
            appender.stop();
        }

        return captured;
    }

    private static class CapturingAppender extends AbstractAppender {
        private final List<LogEvent> events;

        protected CapturingAppender(String name, List<LogEvent> events) {
            super(name, null, null, false, null);
            this.events = events;
        }

        @Override
        public void append(LogEvent event) {
            events.add(event.toImmutable());
        }
    }
}
