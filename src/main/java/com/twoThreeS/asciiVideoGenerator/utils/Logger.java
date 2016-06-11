package com.twoThreeS.asciiVideoGenerator.utils;

public class Logger {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private org.slf4j.Logger logger;

    public Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public void info(Object message) {
        logger.info(ANSI_GREEN + message + ANSI_RESET);
    }

    public void debug(Object message) {
        logger.debug(ANSI_CYAN + message + ANSI_RESET);
    }

    public void debugNoColor(Object message) {
        logger.debug(message.toString());
    }

    public void warn(Object message) {
        logger.warn(ANSI_YELLOW + message + ANSI_RESET);
    }

    public void error(Object message) {
        logger.error(ANSI_RED + message + ANSI_RESET);
    }
}
