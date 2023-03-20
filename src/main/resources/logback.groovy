import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

// LOG_FOLDER decide where to log
def LOG_FOLDER = "."

def PATTERN = "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

appender("FILE", RollingFileAppender) {
    file = "${LOG_FOLDER}/log.txt"
    encoder(PatternLayoutEncoder) { pattern = "${PATTERN}" }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_FOLDER}/archived/log.%d{yyyy-MM-dd}.%i.txt.zip"
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) { maxFileSize = "10MB" }
        maxHistory = 90
        cleanHistoryOnStart = true
    }
}

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "${PATTERN}"
    }
}

root(INFO, ["FILE", "STDOUT"])
logger(DEBUG, ["FILE", "STDOUT"], true)


