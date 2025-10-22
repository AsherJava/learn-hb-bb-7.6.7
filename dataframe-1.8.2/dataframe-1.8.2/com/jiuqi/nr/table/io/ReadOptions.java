/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 */
package com.jiuqi.nr.table.io;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.df.Options;
import com.jiuqi.nr.table.io.ByIdxColumnTypeReadOptions;
import com.jiuqi.nr.table.io.ByNameMapColumnTypeReadOptions;
import com.jiuqi.nr.table.io.CompleteFunctionColumnTypeReadOptions;
import com.jiuqi.nr.table.io.PartialFunctionColumnTypeReadOptions;
import com.jiuqi.nr.table.io.Source;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ReadOptions
implements Options {
    public static final boolean DEFAULT_IGNORE_ZERO_DECIMAL = true;
    public static final boolean DEFAULT_SKIP_ROWS_WITH_INVALID_COLUMN_COUNT = false;
    private static final List<ColumnType> DEFAULT_TYPES = Lists.newArrayList((Object[])new ColumnType[]{ColumnType.BOOLEAN, ColumnType.LONG, ColumnType.DOUBLE, ColumnType.LOCAL_DATE, ColumnType.LOCAL_DATE_TIME, ColumnType.ENUM, ColumnType.STRING, ColumnType.TEXT});
    protected final Source source;
    protected final String tableName;
    protected final int maxCharsPerColumn;
    protected final boolean header;
    protected final ColumnTypeReadOptions columnTypeReadOptions;
    private boolean allowDuplicateColumnNames;
    protected final List<ColumnType> columnTypesToDetect;
    private boolean sample;
    private String[] missingValueIndicators;
    private boolean ignoreZeroDecimal;
    protected final String dateFormat;
    protected final String dateTimeFormat;
    protected final String timeFormat;
    protected final Locale locale;
    protected final DateTimeFormatter dateFormatter;
    protected final DateTimeFormatter dateTimeFormatter;
    protected final DateTimeFormatter timeFormatter;
    private int rowIndexCount;
    private Object[] rowIndexName;

    protected ReadOptions(Builder builder) {
        this.source = builder.source;
        this.tableName = builder.tableName;
        this.header = builder.header;
        this.sample = builder.sample;
        this.dateFormat = builder.dateFormat;
        this.timeFormat = builder.timeFormat;
        this.dateTimeFormat = builder.dateTimeFormat;
        this.locale = builder.locale;
        this.dateFormatter = builder.dateFormatter;
        this.timeFormatter = builder.timeFormatter;
        this.dateTimeFormatter = builder.dateTimeFormatter;
        this.columnTypesToDetect = builder.columnTypesToDetect;
        this.maxCharsPerColumn = builder.maxCharsPerColumn;
        this.allowDuplicateColumnNames = builder.allowDuplicateColumnNames;
        this.missingValueIndicators = builder.missingValueIndicators;
        this.ignoreZeroDecimal = builder.ignoreZeroDecimal;
        this.rowIndexCount = builder.rowIndexCount;
        this.columnTypeReadOptions = builder.columnTypes != null ? new ByIdxColumnTypeReadOptions(builder.columnTypes) : (!builder.columnTypeMap.isEmpty() ? new ByNameMapColumnTypeReadOptions(builder.columnTypeMap) : (builder.completeColumnTypeFunction != null ? new CompleteFunctionColumnTypeReadOptions(builder.completeColumnTypeFunction) : (builder.columnTypeFunction != null ? new PartialFunctionColumnTypeReadOptions(builder.columnTypeFunction) : ColumnTypeReadOptions.EMPTY)));
    }

    public Source source() {
        return this.source;
    }

    public String tableName() {
        return this.tableName;
    }

    public boolean header() {
        return this.header;
    }

    public ColumnTypeReadOptions columnTypeReadOptions() {
        return this.columnTypeReadOptions;
    }

    public int rowIndexCount() {
        return this.rowIndexCount;
    }

    public boolean allowDuplicateColumnNames() {
        return this.allowDuplicateColumnNames;
    }

    public boolean sample() {
        return this.sample;
    }

    public List<ColumnType> columnTypesToDetect() {
        return this.columnTypesToDetect;
    }

    public String[] missingValueIndicators() {
        return this.missingValueIndicators;
    }

    public boolean ignoreZeroDecimal() {
        return this.ignoreZeroDecimal;
    }

    public DateTimeFormatter dateFormatter() {
        if (this.dateFormatter != null) {
            return this.dateFormatter;
        }
        if (Strings.isNullOrEmpty((String)this.dateFormat)) {
            return null;
        }
        return DateTimeFormatter.ofPattern(this.dateFormat, this.locale);
    }

    public Locale locale() {
        return this.locale;
    }

    static /* synthetic */ List access$100() {
        return DEFAULT_TYPES;
    }

    public static interface ColumnTypeReadOptions {
        public static final ColumnTypeReadOptions EMPTY = (columnNumber, columnName) -> Optional.empty();

        public Optional<ColumnType> columnType(int var1, String var2);

        default public boolean hasColumnTypeForAllColumnsIfHavingColumnNames() {
            return false;
        }

        default public boolean hasColumnTypeForAllColumns() {
            return false;
        }

        default public ColumnType[] columnTypes() {
            return null;
        }

        public static ColumnTypeReadOptions of(ColumnType[] allColumnTypes) {
            return new ByIdxColumnTypeReadOptions(allColumnTypes);
        }
    }

    protected static class Builder {
        protected final Source source;
        protected String tableName = "";
        protected List<ColumnType> columnTypesToDetect = ReadOptions.access$100();
        protected boolean sample = true;
        protected String dateFormat;
        protected DateTimeFormatter dateFormatter;
        protected String timeFormat;
        protected DateTimeFormatter timeFormatter;
        protected String dateTimeFormat;
        protected DateTimeFormatter dateTimeFormatter;
        protected Locale locale = Locale.getDefault();
        protected String[] missingValueIndicators = new String[0];
        protected boolean minimizeColumnSizes = false;
        protected boolean header = true;
        protected int maxCharsPerColumn = 4096;
        protected boolean ignoreZeroDecimal = true;
        protected boolean skipRowsWithInvalidColumnCount = false;
        private boolean allowDuplicateColumnNames = false;
        protected ColumnType[] columnTypes;
        protected Map<String, ColumnType> columnTypeMap = new HashMap<String, ColumnType>();
        protected Function<String, Optional<ColumnType>> columnTypeFunction;
        protected Function<String, ColumnType> completeColumnTypeFunction;
        protected int rowIndexCount;

        protected Builder() {
            this.source = null;
        }

        protected Builder(Source source) {
            this.source = source;
        }

        protected Builder(File file) {
            this.source = new Source(file);
            this.tableName = file.getName();
        }

        protected Builder(InputStream stream) {
            this.source = new Source(stream);
        }

        protected Builder(InputStreamReader reader) {
            this.source = new Source(reader);
        }

        protected Builder(Reader reader) {
            this.source = new Source(reader);
        }

        public Builder tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder header(boolean hasHeader) {
            this.header = hasHeader;
            return this;
        }

        public Builder dateFormat(DateTimeFormatter dateFormat) {
            this.dateFormatter = dateFormat;
            return this;
        }

        public Builder allowDuplicateColumnNames(Boolean allow) {
            this.allowDuplicateColumnNames = allow;
            return this;
        }

        public Builder timeFormat(DateTimeFormatter dateFormat) {
            this.timeFormatter = dateFormat;
            return this;
        }

        public Builder dateTimeFormat(DateTimeFormatter dateFormat) {
            this.dateTimeFormatter = dateFormat;
            return this;
        }

        public Builder missingValueIndicator(String ... missingValueIndicators) {
            this.missingValueIndicators = missingValueIndicators;
            return this;
        }

        public Builder maxCharsPerColumn(int maxCharsPerColumn) {
            this.maxCharsPerColumn = maxCharsPerColumn;
            return this;
        }

        public Builder ignoreZeroDecimal(boolean ignoreZeroDecimal) {
            this.ignoreZeroDecimal = ignoreZeroDecimal;
            return this;
        }

        public Builder skipRowsWithInvalidColumnCount(boolean skipRowsWithInvalidColumnCount) {
            this.skipRowsWithInvalidColumnCount = skipRowsWithInvalidColumnCount;
            return this;
        }

        public Builder sample(boolean sample) {
            this.sample = sample;
            return this;
        }

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder rowIndexCount(int rowIndexCount) {
            this.rowIndexCount = rowIndexCount;
            return this;
        }

        public Builder columnTypesToDetect(List<ColumnType> columnTypesToDetect) {
            ArrayList<ColumnType> orderedTypes = new ArrayList<ColumnType>();
            this.columnTypesToDetect = orderedTypes;
            return this;
        }

        public Builder columnTypes(ColumnType[] columnTypes) {
            if (this.columnTypeOptionsAlreadySet()) {
                throw new IllegalStateException("columnTypes already set");
            }
            this.columnTypes = columnTypes;
            return this;
        }

        public Builder columnTypes(Function<String, ColumnType> columnTypeFunction) {
            if (this.columnTypeOptionsAlreadySet()) {
                throw new IllegalStateException("columnTypes already set");
            }
            this.completeColumnTypeFunction = columnTypeFunction;
            return this;
        }

        public Builder columnTypesPartial(Function<String, Optional<ColumnType>> columnTypeFunction) {
            if (this.columnTypeOptionsAlreadySet()) {
                throw new IllegalStateException("columnTypes already set");
            }
            this.columnTypeFunction = columnTypeFunction;
            return this;
        }

        public Builder columnTypesPartial(Map<String, ColumnType> columnTypeByName) {
            if (this.columnTypeOptionsAlreadySet()) {
                throw new IllegalStateException("columnTypes already set");
            }
            if (columnTypeByName != null) {
                this.columnTypeMap = columnTypeByName;
            }
            return this;
        }

        private boolean columnTypeOptionsAlreadySet() {
            return this.columnTypes != null || this.columnTypeFunction != null || this.completeColumnTypeFunction != null || !this.columnTypeMap.isEmpty();
        }

        public ReadOptions build() {
            return new ReadOptions(this);
        }
    }
}

