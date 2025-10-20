/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.logging.LogBuffer;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import java.util.function.Consumer;
import org.slf4j.event.Level;

public class LoggerWriter
extends Writer {
    private final ILogger logger;
    private final Level level;
    private final boolean enabled;
    private final LogBuffer buffer;

    public LoggerWriter(ILogger logger, Level level) {
        super(Objects.requireNonNull(logger));
        Consumer<String> writer;
        this.logger = logger;
        this.level = Objects.requireNonNull(level);
        switch (level) {
            case DEBUG: {
                this.enabled = logger.isDebugEnabled();
                writer = logger::debug;
                break;
            }
            case ERROR: {
                this.enabled = logger.isErrorEnabled();
                writer = logger::error;
                break;
            }
            case INFO: {
                this.enabled = logger.isInfoEnabled();
                writer = logger::info;
                break;
            }
            case TRACE: {
                this.enabled = logger.isTraceEnabled();
                writer = logger::trace;
                break;
            }
            case WARN: {
                this.enabled = logger.isWarnEnabled();
                writer = logger::warn;
                break;
            }
            default: {
                throw new IllegalArgumentException("\u672a\u652f\u6301\u7684\u65e5\u5fd7\u7ea7\u522b\uff1a" + (Object)((Object)level));
            }
        }
        this.buffer = new LogBuffer(writer);
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

    public static Writer of(ILogger logger, Level level) {
        return new LoggerWriter(logger, level);
    }
}

