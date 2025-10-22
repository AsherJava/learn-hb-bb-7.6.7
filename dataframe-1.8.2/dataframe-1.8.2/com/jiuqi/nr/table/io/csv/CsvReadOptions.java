/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io.csv;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.Source;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class CsvReadOptions
extends ReadOptions {
    private final Character separator;
    private final Character quoteChar;
    private final Character escapeChar;
    private final String lineEnding;
    private final Integer maxNumberOfColumns;
    private final Character commentPrefix;
    private final boolean lineSeparatorDetectionEnabled;
    private final int sampleSize;

    private CsvReadOptions(Builder builder) {
        super(builder);
        this.separator = builder.separator;
        this.quoteChar = builder.quoteChar;
        this.escapeChar = builder.escapeChar;
        this.lineEnding = builder.lineEnding;
        this.maxNumberOfColumns = builder.maxNumberOfColumns;
        this.commentPrefix = builder.commentPrefix;
        this.lineSeparatorDetectionEnabled = builder.lineSeparatorDetectionEnabled;
        this.sampleSize = builder.sampleSize;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CsvReadOptions that = (CsvReadOptions)o;
        return this.lineSeparatorDetectionEnabled == that.lineSeparatorDetectionEnabled && this.sampleSize == that.sampleSize && Objects.equals(this.separator, that.separator) && Objects.equals(this.quoteChar, that.quoteChar) && Objects.equals(this.escapeChar, that.escapeChar) && Objects.equals(this.lineEnding, that.lineEnding) && Objects.equals(this.maxNumberOfColumns, that.maxNumberOfColumns) && Objects.equals(this.commentPrefix, that.commentPrefix);
    }

    public int hashCode() {
        return Objects.hash(this.separator, this.quoteChar, this.escapeChar, this.lineEnding, this.maxNumberOfColumns, this.commentPrefix, this.lineSeparatorDetectionEnabled, this.sampleSize);
    }

    public static Builder builder(Source source) {
        return new Builder(source);
    }

    public static Builder builder(File file) {
        return new Builder(file).tableName(file.getName());
    }

    public static Builder builder(String fileName) {
        return new Builder(new File(fileName));
    }

    public static Builder builderFromFile(String fileName) {
        return new Builder(new File(fileName));
    }

    public static Builder builderFromString(String contents) {
        return new Builder(new StringReader(contents));
    }

    public static Builder builder(InputStream stream) {
        return new Builder(stream);
    }

    public static Builder builder(Reader reader) {
        return new Builder(reader);
    }

    public static Builder builder(InputStreamReader reader) {
        return new Builder(reader);
    }

    public Character separator() {
        return this.separator;
    }

    public Character quoteChar() {
        return this.quoteChar;
    }

    public Character escapeChar() {
        return this.escapeChar;
    }

    public String lineEnding() {
        return this.lineEnding;
    }

    public boolean lineSeparatorDetectionEnabled() {
        return this.lineSeparatorDetectionEnabled;
    }

    public Integer maxNumberOfColumns() {
        return this.maxNumberOfColumns;
    }

    public Character commentPrefix() {
        return this.commentPrefix;
    }

    public int sampleSize() {
        return this.sampleSize;
    }

    public int maxCharsPerColumn() {
        return this.maxCharsPerColumn;
    }

    public static class Builder
    extends ReadOptions.Builder {
        private Character separator;
        private Character quoteChar;
        private Character escapeChar;
        private String lineEnding;
        private Integer maxNumberOfColumns = 10000;
        private Character commentPrefix;
        private boolean lineSeparatorDetectionEnabled = true;
        private int sampleSize = -1;

        protected Builder(Source source) {
            super(source);
        }

        protected Builder(File file) {
            super(file);
        }

        protected Builder(InputStreamReader reader) {
            super(reader);
        }

        protected Builder(Reader reader) {
            super(reader);
        }

        protected Builder(InputStream stream) {
            super(stream);
        }

        @Override
        public Builder columnTypes(ColumnType[] columnTypes) {
            super.columnTypes(columnTypes);
            return this;
        }

        @Override
        public Builder columnTypes(Function<String, ColumnType> columnTypeFunction) {
            super.columnTypes(columnTypeFunction);
            return this;
        }

        @Override
        public Builder columnTypesPartial(Function<String, Optional<ColumnType>> columnTypeFunction) {
            super.columnTypesPartial(columnTypeFunction);
            return this;
        }

        @Override
        public Builder columnTypesPartial(Map<String, ColumnType> columnTypeByName) {
            super.columnTypesPartial(columnTypeByName);
            return this;
        }

        public Builder separator(Character separator) {
            this.separator = separator;
            return this;
        }

        public Builder quoteChar(Character quoteChar) {
            this.quoteChar = quoteChar;
            return this;
        }

        public Builder escapeChar(Character escapeChar) {
            this.escapeChar = escapeChar;
            return this;
        }

        public Builder lineEnding(String lineEnding) {
            this.lineEnding = lineEnding;
            this.lineSeparatorDetectionEnabled = false;
            return this;
        }

        public Builder maxNumberOfColumns(Integer maxNumberOfColumns) {
            this.maxNumberOfColumns = maxNumberOfColumns;
            return this;
        }

        public Builder commentPrefix(Character commentPrefix) {
            this.commentPrefix = commentPrefix;
            return this;
        }

        public Builder sampleSize(int numSamples) {
            this.sampleSize = numSamples;
            return this;
        }

        @Override
        public CsvReadOptions build() {
            return new CsvReadOptions(this);
        }

        @Override
        public Builder header(boolean header) {
            super.header(header);
            return this;
        }

        @Override
        public Builder allowDuplicateColumnNames(Boolean allow) {
            super.allowDuplicateColumnNames(allow);
            return this;
        }

        @Override
        public Builder tableName(String tableName) {
            super.tableName(tableName);
            return this;
        }

        @Override
        public Builder sample(boolean sample) {
            super.sample(sample);
            return this;
        }

        @Override
        public Builder dateFormat(DateTimeFormatter dateFormat) {
            super.dateFormat(dateFormat);
            return this;
        }

        @Override
        public Builder timeFormat(DateTimeFormatter timeFormat) {
            super.timeFormat(timeFormat);
            return this;
        }

        @Override
        public Builder dateTimeFormat(DateTimeFormatter dateTimeFormat) {
            super.dateTimeFormat(dateTimeFormat);
            return this;
        }

        @Override
        public Builder maxCharsPerColumn(int maxCharsPerColumn) {
            super.maxCharsPerColumn(maxCharsPerColumn);
            return this;
        }

        @Override
        public Builder locale(Locale locale) {
            super.locale(locale);
            return this;
        }

        @Override
        public Builder missingValueIndicator(String ... missingValueIndicators) {
            super.missingValueIndicator(missingValueIndicators);
            return this;
        }

        @Override
        public Builder ignoreZeroDecimal(boolean ignoreZeroDecimal) {
            super.ignoreZeroDecimal(ignoreZeroDecimal);
            return this;
        }

        @Override
        public Builder skipRowsWithInvalidColumnCount(boolean skipRowsWithInvalidColumnCount) {
            super.skipRowsWithInvalidColumnCount(skipRowsWithInvalidColumnCount);
            return this;
        }

        @Override
        public Builder rowIndexCount(int indexCount) {
            super.rowIndexCount(indexCount);
            return this;
        }
    }
}

