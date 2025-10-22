/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io.json;

import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.Source;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class JsonReadOptions
extends ReadOptions {
    private final String path;

    protected JsonReadOptions(Builder builder) {
        super(builder);
        this.path = builder.path;
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

    public String path() {
        return this.path;
    }

    public static class Builder
    extends ReadOptions.Builder {
        private String path;

        protected Builder(Source source) {
            super(source);
        }

        public Builder(File file) {
            super(file);
        }

        protected Builder(Reader reader) {
            super(reader);
        }

        protected Builder(InputStream stream) {
            super(stream);
        }

        @Override
        public JsonReadOptions build() {
            return new JsonReadOptions(this);
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

        public Builder path(String path) {
            this.path = path;
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
        public Builder columnTypesPartial(Function<String, Optional<ColumnType>> columnTypeFunction) {
            super.columnTypesPartial(columnTypeFunction);
            return this;
        }

        @Override
        public Builder columnTypesPartial(Map<String, ColumnType> columnTypeByName) {
            super.columnTypesPartial(columnTypeByName);
            return this;
        }
    }
}

