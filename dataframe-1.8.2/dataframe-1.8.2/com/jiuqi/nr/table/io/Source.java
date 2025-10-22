/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ibm.icu.text.CharsetDetector
 *  com.ibm.icu.text.CharsetMatch
 */
package com.jiuqi.nr.table.io;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;

public class Source {
    protected final File file;
    protected final Reader reader;
    protected final InputStream inputStream;
    protected final Charset charset;

    public Source(File file) {
        this(file, Source.getCharSet(file));
    }

    public Source(File file, Charset charset) {
        this.file = file;
        this.reader = null;
        this.inputStream = null;
        this.charset = charset;
    }

    public Source(InputStreamReader reader) {
        this.file = null;
        this.reader = reader;
        this.inputStream = null;
        this.charset = Charset.forName(reader.getEncoding());
    }

    public Source(Reader reader) {
        this.file = null;
        this.reader = reader;
        this.inputStream = null;
        this.charset = null;
    }

    public Source(InputStream inputStream) {
        this(inputStream, Charset.defaultCharset());
    }

    public Source(InputStream inputStream, Charset charset) {
        this.file = null;
        this.reader = null;
        this.inputStream = inputStream;
        this.charset = charset;
    }

    public static Source fromString(String s) {
        return new Source(new StringReader(s));
    }

    public File file() {
        return this.file;
    }

    public Reader reader() {
        return this.reader;
    }

    public InputStream inputStream() {
        return this.inputStream;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public Reader createReader(byte[] cachedBytes) throws IOException {
        if (cachedBytes != null) {
            return this.charset != null ? new InputStreamReader((InputStream)new ByteArrayInputStream(cachedBytes), this.charset) : new InputStreamReader(new ByteArrayInputStream(cachedBytes));
        }
        if (this.inputStream != null) {
            return new InputStreamReader(this.inputStream, this.charset);
        }
        if (this.reader != null) {
            return this.reader;
        }
        return new InputStreamReader((InputStream)new FileInputStream(this.file), this.charset);
    }

    static Charset getCharSet(File file) {
        long bufferSize = file.length() < 9999L ? file.length() : 9999L;
        byte[] buffer = new byte[(int)bufferSize];
        try (FileInputStream initialStream = new FileInputStream(file);){
            int bytesRead = ((InputStream)initialStream).read(buffer);
            if ((long)bytesRead < bufferSize) {
                throw new IOException("Was not able to read expected number of bytes");
            }
        }
        catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return Source.getCharSet(buffer);
    }

    private static Charset getCharSet(byte[] buffer) {
        CharsetDetector detector = new CharsetDetector();
        detector.setText(buffer);
        CharsetMatch match = detector.detect();
        if (match == null || match.getConfidence() < 60) {
            return Charset.defaultCharset();
        }
        return Charset.forName(match.getName());
    }
}

