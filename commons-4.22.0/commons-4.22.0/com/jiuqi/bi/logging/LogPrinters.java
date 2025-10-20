/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.logging.LogBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LogPrinters {
    private LogPrinters() {
    }

    public static PrintStream createPrintStream(ILogger logger, Level level) {
        OutputStream outStream;
        switch (level) {
            case DEBUG: {
                outStream = logger.isDebugEnabled() ? new PrintOutputStream(logger::debug) : new EmptyOutputStream();
                break;
            }
            case ERROR: {
                outStream = logger.isErrorEnabled() ? new PrintOutputStream(logger::error) : new EmptyOutputStream();
                break;
            }
            case INFO: {
                outStream = logger.isInfoEnabled() ? new PrintOutputStream(logger::info) : new EmptyOutputStream();
                break;
            }
            case TRACE: {
                outStream = logger.isTraceEnabled() ? new PrintOutputStream(logger::trace) : new EmptyOutputStream();
                break;
            }
            case WARN: {
                outStream = logger.isWarnEnabled() ? new PrintOutputStream(logger::warn) : new EmptyOutputStream();
                break;
            }
            default: {
                throw new IllegalArgumentException("level = " + (Object)((Object)level));
            }
        }
        try {
            return new PrintStream(outStream, false, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported encoding: UTF-8", e);
        }
    }

    public static PrintStream createPrintStream(Logger logger, Level level) {
        OutputStream outStream;
        switch (level) {
            case DEBUG: {
                outStream = logger.isDebugEnabled() ? new PrintOutputStream(logger::debug) : new EmptyOutputStream();
                break;
            }
            case ERROR: {
                outStream = logger.isErrorEnabled() ? new PrintOutputStream(logger::error) : new EmptyOutputStream();
                break;
            }
            case INFO: {
                outStream = logger.isInfoEnabled() ? new PrintOutputStream(logger::info) : new EmptyOutputStream();
                break;
            }
            case TRACE: {
                outStream = logger.isTraceEnabled() ? new PrintOutputStream(logger::trace) : new EmptyOutputStream();
                break;
            }
            case WARN: {
                outStream = logger.isWarnEnabled() ? new PrintOutputStream(logger::warn) : new EmptyOutputStream();
                break;
            }
            default: {
                throw new IllegalArgumentException("level = " + (Object)((Object)level));
            }
        }
        try {
            return new PrintStream(outStream, false, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported encoding: UTF-8", e);
        }
    }

    private static final class EmptyOutputStream
    extends OutputStream {
        private EmptyOutputStream() {
        }

        @Override
        public void write(int b) throws IOException {
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
        }
    }

    private static final class PrintOutputStream
    extends OutputStream {
        private final LogBuffer buffer;
        private final CharsetDecoder decoder;

        public PrintOutputStream(LogBuffer buffer) {
            this.buffer = buffer;
            this.decoder = StandardCharsets.UTF_8.newDecoder();
        }

        public PrintOutputStream(Consumer<String> wirtier) {
            this(new LogBuffer(wirtier));
        }

        @Override
        public void write(int b) throws IOException {
            byte[] buf = new byte[]{(byte)b};
            this.write(buf);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            CharBuffer chars = this.decoder.decode(ByteBuffer.wrap(b, off, len));
            this.buffer.append(chars.array(), 0, chars.length());
        }

        @Override
        public void flush() throws IOException {
            this.buffer.flush();
        }

        @Override
        public void close() throws IOException {
            this.flush();
        }
    }
}

