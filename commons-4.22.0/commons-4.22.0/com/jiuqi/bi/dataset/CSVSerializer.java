/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BlobValue;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public final class CSVSerializer {
    private DataSet<?> dataset;
    private int options;
    private Function<Column<?>, ? extends Format> formatProvider;

    public CSVSerializer(DataSet<?> dataset) {
        this.dataset = dataset;
    }

    public CSVSerializer options(int options) {
        this.options = options;
        return this;
    }

    public CSVSerializer formatProvider(Function<Column<?>, ? extends Format> provider) {
        this.formatProvider = provider;
        return this;
    }

    public void save(Writer writer) throws IOException {
        if ((this.options & 1) == 0) {
            for (Column<?> column : this.dataset.getMetadata()) {
                if (column.getIndex() > 0) {
                    writer.write(",");
                }
                writer.write(column.getName());
            }
            writer.write(StringUtils.LINE_SEPARATOR);
        }
        List<DataFormator> formaters = this.createCSVFormators((this.options & 4) != 0);
        for (DataRow row : this.dataset) {
            for (int i = 0; i < formaters.size(); ++i) {
                Object value = row.getValue(i);
                if (i > 0) {
                    writer.write(",");
                }
                if (value == null) continue;
                String expr = formaters.get(i).format(value);
                writer.write(expr);
            }
            writer.write(StringUtils.LINE_SEPARATOR);
        }
    }

    public void load(Reader reader) throws IOException, DataSetException {
        List<String> record;
        this.dataset.clear();
        CVSReader csv = new CVSReader(reader);
        int[] colMaps = null;
        if ((this.options & 1) == 0) {
            String header = csv.skipLine();
            if ((this.options & 2) != 0) {
                colMaps = this.mapColumns(header);
            }
        }
        boolean siftNull = (this.options & 8) != 0;
        List<DataFormator> formaters = this.createCSVFormators((this.options & 4) != 0);
        this.dataset.beginUpdate();
        while ((record = csv.readRecord()) != null) {
            DataRow row = this.dataset.add();
            for (int i = 0; i < Math.min(formaters.size(), record.size()); ++i) {
                int dest;
                int n = dest = colMaps == null ? i : colMaps[i];
                if (dest == -1) continue;
                String expr = record.get(i);
                if (siftNull) {
                    int dType = this.dataset.getMetadata().getColumn(dest).getDataType();
                    if (expr == null || expr.length() == 0) {
                        if (dType == 6) {
                            row.setValue(dest, "");
                            continue;
                        }
                        row.setValue(dest, null);
                        continue;
                    }
                    if (expr.equalsIgnoreCase("NULL") && dType == 6) {
                        if (csv.containEnclosedChar(i)) {
                            row.setValue(dest, expr);
                            continue;
                        }
                        row.setValue(dest, null);
                        continue;
                    }
                    Object value = formaters.get(dest).parse(expr);
                    row.setValue(dest, value);
                    continue;
                }
                if (expr == null || expr.length() == 0) {
                    row.setValue(dest, null);
                    continue;
                }
                Object value = formaters.get(dest).parse(expr);
                row.setValue(dest, value);
            }
            if (row.commit()) continue;
            break;
        }
        this.dataset.endUpdate();
    }

    private int[] mapColumns(String header) throws IOException {
        if (header == null || header.length() == 0) {
            return null;
        }
        String[] colNames = header.split(",");
        int count = 0;
        int[] maps = new int[Math.max(colNames.length, this.dataset.getMetadata().size())];
        Arrays.fill(maps, -1);
        for (int i = 0; i < colNames.length; ++i) {
            int index = this.dataset.getMetadata().indexOf(colNames[i]);
            if (index < 0) continue;
            maps[i] = index;
            ++count;
        }
        if (count == 0) {
            throw new IOException("\u5f53\u524d\u6570\u636e\u96c6\u5b57\u6bb5\u4e0eCSV\u6587\u4ef6\u5b57\u6bb5\u65e0\u6cd5\u8fdb\u884c\u6620\u5c04\u3002");
        }
        return maps;
    }

    private List<DataFormator> createCSVFormators(boolean supportAny) throws IOException {
        ArrayList<DataFormator> formaters = new ArrayList<DataFormator>();
        block11: for (Column<?> column : this.dataset.getMetadata()) {
            Format format;
            if (this.formatProvider != null && (format = this.formatProvider.apply(column)) != null) {
                formaters.add(new CustomFormator(format));
                continue;
            }
            switch (column.getDataType()) {
                case 6: {
                    formaters.add(new StringFormator());
                    continue block11;
                }
                case 3: {
                    formaters.add(new DoubleFormator());
                    continue block11;
                }
                case 5: {
                    formaters.add(new IntegerFormator());
                    continue block11;
                }
                case 2: {
                    formaters.add(new DateFormator());
                    continue block11;
                }
                case 10: {
                    formaters.add(new BigDecimalFormator());
                    continue block11;
                }
                case 1: {
                    formaters.add(new BooleanFormator());
                    continue block11;
                }
                case 9: {
                    formaters.add(new BlobFormator());
                    continue block11;
                }
                case 8: {
                    formaters.add(new LongFormator());
                    continue block11;
                }
                case 0: {
                    if (!supportAny) {
                        throw new IOException("\u5b57\u6bb5[" + column.getName() + "]\u6570\u636e\u7c7b\u578b\u672a\u77e5\uff0c\u65e0\u6cd5\u652f\u6301\u3002");
                    }
                    formaters.add(new AnyFormator());
                    continue block11;
                }
            }
            throw new IOException("\u5b57\u6bb5[" + column.getName() + "]\u7684\u6570\u636e\u7c7b\u578b(" + column.getDataType() + ")\u5c1a\u672a\u652f\u6301\u3002");
        }
        return formaters;
    }

    private static final class CVSReader {
        private BufferedReader reader;
        private String line;
        private List<String> record;

        public CVSReader(Reader reader) {
            this.reader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
            this.record = new ArrayList<String>();
        }

        public String skipLine() throws IOException {
            this.line = this.reader.readLine();
            return this.line;
        }

        public List<String> readRecord() throws IOException {
            this.record.clear();
            do {
                this.line = this.reader.readLine();
                if (this.line != null) continue;
                return null;
            } while (this.line.trim().length() == 0);
            int next = 0;
            while (next != -1) {
                next = this.readColumn(next);
            }
            return this.record;
        }

        public boolean containEnclosedChar(int columnIdx) throws IOException {
            int next = 0;
            int idx = 0;
            while (next != -1) {
                if (columnIdx == idx) {
                    return this.line.charAt(next) == '\"';
                }
                next = this.readCharPos(next);
                ++idx;
            }
            return false;
        }

        private int readColumn(int start) throws IOException {
            if (start >= this.line.length()) {
                return -1;
            }
            if (this.line.charAt(start) == '\"') {
                StringBuilder buffer = new StringBuilder();
                int next = this.readQuoteString(buffer, start);
                if (this.line == null || next == -1 || next >= this.line.length()) {
                    this.record.add(buffer.toString());
                    return -1;
                }
                int p = this.line.indexOf(44, next);
                if (p == -1) {
                    buffer.append(this.line.substring(next));
                    this.record.add(buffer.toString());
                    return -1;
                }
                buffer.append(this.line.substring(next, p));
                this.record.add(buffer.toString());
                return p + 1;
            }
            int next = this.line.indexOf(44, start);
            if (next == -1) {
                this.record.add(this.line.substring(start));
                return -1;
            }
            this.record.add(this.line.substring(start, next));
            return next + 1;
        }

        private int readCharPos(int start) throws IOException {
            if (start >= this.line.length()) {
                return -1;
            }
            if (this.line.charAt(start) == '\"') {
                StringBuilder buffer = new StringBuilder();
                int next = this.readQuoteString(buffer, start);
                if (this.line == null || next == -1 || next >= this.line.length()) {
                    return -1;
                }
                int p = this.line.indexOf(44, next);
                if (p == -1) {
                    return -1;
                }
                return p + 1;
            }
            int next = this.line.indexOf(44, start);
            if (next == -1) {
                return -1;
            }
            return next + 1;
        }

        private int readQuoteString(StringBuilder buffer, int start) throws IOException {
            boolean ended = false;
            int cur = start + 1;
            while (!ended) {
                char ch;
                if (cur >= this.line.length()) {
                    do {
                        this.line = this.reader.readLine();
                        if (this.line == null) {
                            return -1;
                        }
                        buffer.append(StringUtils.LINE_SEPARATOR);
                    } while (this.line.length() == 0);
                    cur = 0;
                }
                if ((ch = this.line.charAt(cur)) == '\"') {
                    if (cur < this.line.length() - 1 && this.line.charAt(cur + 1) == '\"') {
                        buffer.append('\"');
                        cur += 2;
                        continue;
                    }
                    ++cur;
                    ended = true;
                    continue;
                }
                buffer.append(ch);
                ++cur;
            }
            return ended ? cur : -1;
        }
    }

    private static final class CustomFormator
    extends DataFormator {
        private final Format format;

        public CustomFormator(Format format) {
            this.format = format;
        }

        @Override
        public String format(Object value) {
            if (value == null) {
                return null;
            }
            return this.format.format(value);
        }

        @Override
        public Object parse(String expr) {
            if (expr == null || expr.isEmpty()) {
                return null;
            }
            try {
                return this.format.parseObject(expr);
            }
            catch (ParseException e) {
                throw new IllegalArgumentException("\u65e0\u6cd5\u89e3\u6790\u7684\u5b57\u6bb5\u503c\uff1a" + expr, e);
            }
        }
    }

    private static final class AnyFormator
    extends DataFormator {
        private AnyFormator() {
        }

        @Override
        public String format(Object value) {
            return value == null ? null : value.toString();
        }

        @Override
        public Object parse(String expr) {
            return expr;
        }
    }

    private static final class BlobFormator
    extends DataFormator {
        private BlobFormator() {
        }

        @Override
        public String format(Object value) {
            return ((BlobValue)value).toBase64();
        }

        @Override
        public Object parse(String expr) {
            return new BlobValue(expr, "BASE64");
        }
    }

    private static final class StringFormator
    extends DataFormator {
        private StringFormator() {
        }

        @Override
        public String format(Object value) {
            String str = value.toString();
            if (this.needQuote(str)) {
                return this.quoteString(str);
            }
            return str;
        }

        private boolean needQuote(String str) {
            for (int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);
                if (ch != '\"' && ch != ',' && ch != '\r' && ch != '\n') continue;
                return true;
            }
            return false;
        }

        private String quoteString(String str) {
            StringBuilder buffer = new StringBuilder(str.length() + 8);
            buffer.append('\"');
            for (int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);
                if (ch == '\"') {
                    buffer.append("\"\"");
                    continue;
                }
                buffer.append(ch);
            }
            buffer.append('\"');
            return buffer.toString();
        }

        @Override
        public Object parse(String expr) {
            return expr;
        }
    }

    private static final class BigDecimalFormator
    extends DataFormator {
        private BigDecimalFormator() {
        }

        @Override
        public String format(Object value) {
            return value.toString();
        }

        @Override
        public Object parse(String expr) {
            return new BigDecimal(expr);
        }
    }

    private static final class LongFormator
    extends DataFormator {
        private LongFormator() {
        }

        @Override
        public String format(Object value) {
            return value.toString();
        }

        @Override
        public Object parse(String expr) {
            return Long.valueOf(expr);
        }
    }

    private static final class IntegerFormator
    extends DataFormator {
        private IntegerFormator() {
        }

        @Override
        public String format(Object value) {
            return value.toString();
        }

        @Override
        public Object parse(String expr) {
            return Integer.valueOf(expr);
        }
    }

    private static final class DoubleFormator
    extends DataFormator {
        private DoubleFormator() {
        }

        @Override
        public String format(Object value) {
            return DataSet.formatNumber(((Number)value).doubleValue());
        }

        @Override
        public Object parse(String expr) {
            return Double.parseDouble(expr);
        }
    }

    private static final class DateFormator
    extends DataFormator {
        private final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        private DateFormator() {
        }

        @Override
        public String format(Object value) {
            return this.fmt.format(((Calendar)value).getTime());
        }

        @Override
        public Object parse(String expr) {
            Date date;
            try {
                date = this.fmt.parse(expr);
            }
            catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
    }

    private static final class BooleanFormator
    extends DataFormator {
        private BooleanFormator() {
        }

        @Override
        public String format(Object value) {
            return (Boolean)value != false ? "1" : "0";
        }

        @Override
        public Object parse(String expr) {
            try {
                int v = Integer.valueOf(expr);
                return v != 0;
            }
            catch (NumberFormatException e) {
                return "true".equalsIgnoreCase(expr);
            }
        }
    }

    private static abstract class DataFormator {
        private DataFormator() {
        }

        public abstract String format(Object var1);

        public abstract Object parse(String var1);
    }
}

