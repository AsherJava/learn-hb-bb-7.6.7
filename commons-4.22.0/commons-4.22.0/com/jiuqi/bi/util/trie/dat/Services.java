/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie.dat;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class Services {
    public static Logger logger = null;

    Services() {
    }

    public static void initLogger(Level level) {
        logger = Logger.getLogger("jqdn");
        logger.setLevel(level);
        logger.setUseParentHandlers(false);
        Handler[] handlers = logger.getHandlers();
        for (int i = 0; i < handlers.length; ++i) {
            Handler handler = handlers[i];
            logger.removeHandler(handler);
        }
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter(){

            @Override
            public String format(LogRecord record) {
                return String.format("%tT\t%s\t%s\n", record.getMillis(), record.getLevel().getName(), record.getMessage());
            }
        });
        logger.addHandler(consoleHandler);
    }
}

