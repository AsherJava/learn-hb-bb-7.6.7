/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io.csv;

import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriteOptions;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Paths;

public class CsvWriteOptions
extends WriteOptions {
    private final boolean header;
    private final boolean ignoreLeadingWhitespaces;
    private final boolean ignoreTrailingWhitespaces;
    private final boolean usePrintFormatter;
    private final Character separator;
    private final Character quoteChar;
    private final Character escapeChar;
    private final String lineEnd;
    private final boolean quoteAllFields;
    private boolean colHeader;

    protected CsvWriteOptions(Builder builder) {
        super(builder);
        this.header = builder.header;
        this.usePrintFormatter = builder.usePrintFormatters;
        this.separator = builder.separator;
        this.quoteChar = builder.quoteChar;
        this.escapeChar = builder.escapeChar;
        this.lineEnd = builder.lineEnd;
        this.ignoreLeadingWhitespaces = builder.ignoreLeadingWhitespaces;
        this.ignoreTrailingWhitespaces = builder.ignoreTrailingWhitespaces;
        this.quoteAllFields = builder.quoteAllFields;
        this.colHeader = builder.colHeader;
    }

    public boolean header() {
        return this.header;
    }

    public boolean colHeader() {
        return this.colHeader;
    }

    public boolean ignoreLeadingWhitespaces() {
        return this.ignoreLeadingWhitespaces;
    }

    public boolean ignoreTrailingWhitespaces() {
        return this.ignoreTrailingWhitespaces;
    }

    public Character separator() {
        return this.separator;
    }

    public Character escapeChar() {
        return this.escapeChar;
    }

    public boolean quoteAllFields() {
        return this.quoteAllFields;
    }

    public boolean usePrintFormatters() {
        return this.usePrintFormatter;
    }

    public Character quoteChar() {
        return this.quoteChar;
    }

    public String lineEnd() {
        return this.lineEnd;
    }

    public boolean autoClose() {
        return this.autoClose;
    }

    public static Builder builder(Destination dest) {
        return new Builder(dest);
    }

    public static Builder builder(OutputStream dest) {
        return new Builder(dest);
    }

    public static Builder builder(Writer dest) {
        return new Builder(dest);
    }

    public static Builder builder(File dest) throws IOException {
        return new Builder(dest);
    }

    public static Builder builder(String fileName) throws IOException {
        return CsvWriteOptions.builder(new File(fileName));
    }

    public static class Builder
    extends WriteOptions.Builder {
        private boolean header = true;
        private boolean ignoreLeadingWhitespaces = true;
        private boolean ignoreTrailingWhitespaces = true;
        private boolean quoteAllFields = false;
        private boolean usePrintFormatters = false;
        private Character separator;
        private String lineEnd = System.lineSeparator();
        private Character escapeChar;
        private Character quoteChar;
        private boolean colHeader;

        protected Builder(String fileName) throws IOException {
            super(Paths.get(fileName, new String[0]).toFile());
        }

        protected Builder(Destination dest) {
            super(dest);
        }

        protected Builder(File file) throws IOException {
            super(file);
        }

        protected Builder(Writer writer) {
            super(writer);
        }

        protected Builder(OutputStream stream) {
            super(stream);
        }

        public Builder separator(char separator) {
            this.separator = Character.valueOf(separator);
            return this;
        }

        public Builder quoteAllFields(boolean quoteAll) {
            this.quoteAllFields = quoteAll;
            return this;
        }

        public Builder escapeChar(char escapeChar) {
            this.escapeChar = Character.valueOf(escapeChar);
            return this;
        }

        public Builder lineEnd(String lineEnd) {
            this.lineEnd = lineEnd;
            return this;
        }

        public Builder header(boolean header) {
            this.header = header;
            return this;
        }

        public Builder colHeader(boolean colHeader) {
            this.colHeader = colHeader;
            return this;
        }

        public Builder ignoreLeadingWhitespaces(boolean ignoreLeadingWhitespaces) {
            this.ignoreLeadingWhitespaces = ignoreLeadingWhitespaces;
            return this;
        }

        public Builder ignoreTrailingWhitespaces(boolean ignoreTrailingWhitespaces) {
            this.ignoreTrailingWhitespaces = ignoreTrailingWhitespaces;
            return this;
        }

        public Builder usePrintFormatters(boolean useFormatter) {
            this.usePrintFormatters = useFormatter;
            return this;
        }

        public CsvWriteOptions build() {
            return new CsvWriteOptions(this);
        }
    }
}

