package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.api.LogLevel;
import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatterForClient {


    public static PersistedString format(final LogMessage log) {
        final String payload = String.format("[%s] %s", log.getLogLevel(), log.getMessage());
        return new PersistedString(log.getTimeStamp(), payload);
    }


    public static LogMessage parse(final PersistedString str) {
        final String payload = str.getPayload();
        final Pattern pattern = Pattern.compile("^\\[(.+)\\] (.+)$");
        final Matcher matcher = pattern.matcher(payload);
        if (!matcher.matches() || matcher.groupCount() < 2) {
            return null;
        }

        final String stringLevel = matcher.group(1);
        LogLevel level = null;

        switch (stringLevel) {
            case "INFO":
                level = LogLevel.INFO;
                break;

            case "ERROR":
                level = LogLevel.ERROR;
                break;

            case "WARN":
                level = LogLevel.WARN;
                break;

            case "FATAL":
                level = LogLevel.FATAL;
                break;

            default:
                level = LogLevel.DEBUG;
        }
        final String message = matcher.group(2);
        final LogMessage parsed = new LogMessage(message, level, str.getTimestamp());
        return parsed;
    }
}
