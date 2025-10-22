/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io.excel;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.Source;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class XlsxReadOptions
extends ReadOptions {
    protected Integer sheetIndex;

    protected XlsxReadOptions(Builder builder) {
        super(builder);
        this.sheetIndex = builder.sheetIndex;
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

    public static Builder builder(InputStream reader) {
        return new Builder(reader);
    }

    public Integer sheetIndex() {
        return this.sheetIndex;
    }

    public static class Builder
    extends ReadOptions.Builder {
        protected Integer sheetIndex;

        protected Builder(Source source) {
            super(source);
        }

        public Builder(File file) {
            super(file);
        }

        public Builder(InputStream stream) {
            super(stream);
        }

        public Builder(Reader reader) {
            super(reader);
        }

        @Override
        public XlsxReadOptions build() {
            return new XlsxReadOptions(this);
        }

        @Override
        public Builder header(boolean header) {
            super.header(header);
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
        public Builder locale(Locale locale) {
            super.locale(locale);
            return this;
        }

        @Override
        public Builder missingValueIndicator(String ... missingValueIndicators) {
            super.missingValueIndicator(missingValueIndicators);
            return this;
        }

        public Builder sheetIndex(int sheetIndex) {
            this.sheetIndex = sheetIndex;
            return this;
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
        public Builder columnTypesPartial(Map<String, ColumnType> columnTypeByName) {
            super.columnTypesPartial(columnTypeByName);
            return this;
        }

        @Override
        public Builder allowDuplicateColumnNames(Boolean allow) {
            super.allowDuplicateColumnNames(allow);
            return this;
        }
    }
}

