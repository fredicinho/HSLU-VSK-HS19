package ch.hslu.vsk.logger.common;


import ch.hslu.vsk.logger.api.LogLevel;
import java.io.Serializable;
import java.time.Instant;

public class LogMessage implements Serializable {
        private String message;
        private LogLevel loglevel;
        private Instant timeLogged;
        private Instant timeServerReceivedLog;
        private static final long serialVersionUID = 6529685098267757690L;

        public LogMessage(final String message, final LogLevel loglevel) {
            this.message = message;
            this.loglevel = loglevel;
            this.timeLogged = Instant.now();
            this.timeServerReceivedLog = Instant.now();
        }

        public LogMessage(final String message, final LogLevel logLevel, final Instant instant) {
            this.message = message;
            this.loglevel = logLevel;
            this.timeLogged = instant;
            this.timeServerReceivedLog = Instant.now();
        }

        public LogMessage(final String message, final LogLevel logLevel, 
        					final Instant timeLogged, final Instant timeServerReceivedLog) {
            this.message = message;
            this.loglevel = logLevel;
            this.timeServerReceivedLog = timeServerReceivedLog;
            this.timeLogged = timeLogged;
        }

        public String getMessage() {
            return this.message;
        }

        public LogLevel getLogLevel() {
            return this.loglevel;
        }

        public Instant getTimeStamp() {
            return this.timeLogged;
        }

        public Instant getTimeServerReceivedLog() {
            return this.timeServerReceivedLog;
        }

    @Override
    public String toString() {
        return "LogMessage{"
                + "loglevel= "
                + loglevel
                + ", message= '"
                + message
                + ", timeServerReceivedLog= "
                + timeServerReceivedLog
                + '}';
    }
}
