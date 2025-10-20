/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.LogBuffer;
import com.jiuqi.bi.logging.LogPrinters;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Objects;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class SLFLoggerWriter
extends Writer {
    private final Logger logger;
    private final Level level;
    private final boolean enabled;
    private final LogBuffer buffer;

    public SLFLoggerWriter(Logger logger, Level level) {
        super(Objects.requireNonNull(logger));
        Consumer<String> writer;
        this.logger = logger;
        this.level = Objects.requireNonNull(level);
        switch (level) {
            case DEBUG: {
                writer = logger::debug;
                this.enabled = logger.isDebugEnabled();
                break;
            }
            case ERROR: {
                writer = logger::error;
                this.enabled = logger.isErrorEnabled();
                break;
            }
            case INFO: {
                writer = logger::info;
                this.enabled = logger.isInfoEnabled();
                break;
            }
            case TRACE: {
                writer = logger::trace;
                this.enabled = logger.isTraceEnabled();
                break;
            }
            case WARN: {
                writer = logger::warn;
                this.enabled = logger.isWarnEnabled();
                break;
            }
            default: {
                throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u65e5\u5fd7\u7ea7\u522b\uff1a" + (Object)((Object)level));
            }
        }
        this.buffer = new LogBuffer(writer);
    }

    @Deprecated
    public static Writer createWriter(Logger logger, Level level) {
        return new SLFLoggerWriter(logger, level);
    }

    @Deprecated
    public static PrintStream createPrintStream(Logger logger, Level level) {
        return LogPrinters.createPrintStream(logger, level);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (this.enabled) {
            this.buffer.append(cbuf, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        this.buffer.flush();
    }

    @Override
    public void close() throws IOException {
        this.buffer.flush();
    }

    public String toString() {
        return (Object)((Object)this.level) + "@" + this.logger;
    }

    public static Writer of(Logger logger, Level level) {
        return new SLFLoggerWriter(logger, level);
    }
}

