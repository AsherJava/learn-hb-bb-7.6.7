/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class DS_LookUp
extends DataSetFunction {
    private static final long serialVersionUID = 1L;
    private static final Map<String, ValueReader> readerMap = new HashMap<String, ValueReader>();

    public DS_LookUp() {
        this.parameters().add(new Parameter("value", 0, "\u67e5\u627e\u503c"));
        this.parameters().add(new Parameter("lookupDS", 5050, "\u67e5\u627e\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("lookupCol", 0, "\u67e5\u627e\u5217\uff0c\u53ef\u4ee5\u4e3a\u5b57\u6bb5\u5bf9\u8c61\u6216\u5b57\u6bb5\u540d"));
        this.parameters().add(new Parameter("returnCol", 0, "\u8fd4\u56de\u5217\uff0c\u53ef\u4ee5\u4e3a\u5b57\u6bb5\u5bf9\u8c61\u6216\u5b57\u6bb5\u540d", true));
        this.parameters().add(new Parameter("statMode", 6, "\u8fd4\u56de\u591a\u503c\u65f6\u7684\u7edf\u8ba1\u65b9\u5f0f\uff0c\u5305\u62ec\uff1aSUM\u3001FIRST\u3001LAST\u3001MIN\u3001MAX\u3001AVG\u3001COUNT", true));
    }

    public String name() {
        return "DS_LookUp";
    }

    public String title() {
        return "\u6570\u636e\u96c6\u67e5\u627e\u51fd\u6570\uff0c\u6839\u636e\u6307\u5b9a\u5217\u67e5\u627e\u6570\u503c\u3002";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters == null || parameters.size() == 0) {
            return 0;
        }
        if (parameters.size() >= 4) {
            return parameters.get(3).getType(context);
        }
        return parameters.get(2).getType(context);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object lookupVal = parameters.get(0).evaluate(context);
        if (lookupVal == null) {
            return null;
        }
        BIDataSet dataSet = (BIDataSet)parameters.get(1).evaluate(context);
        int lookupCol = DS_LookUp.findColumn(context, dataSet, parameters.get(2));
        int returnCol = parameters.size() >= 4 ? DS_LookUp.findColumn(context, dataSet, parameters.get(3)) : lookupCol;
        List rows = dataSet.lookup(lookupCol, lookupVal);
        ValueReader reader = this.getReader(context, dataSet, parameters, returnCol);
        return reader.read(dataSet, rows, returnCol);
    }

    private ValueReader getReader(IContext context, BIDataSet dataSet, List<IASTNode> parameters, int returnCol) throws SyntaxException {
        if (parameters.size() < 5) {
            Column column = dataSet.getMetadata().getColumn(returnCol);
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.MEASURE) {
                switch (column.getDataType()) {
                    case 3: 
                    case 5: 
                    case 10: {
                        return readerMap.get("SUM");
                    }
                }
                return readerMap.get("FIRST");
            }
            return readerMap.get("FIRST");
        }
        String mode = (String)parameters.get(4).evaluate(context);
        ValueReader reader = readerMap.get(mode.toUpperCase());
        if (reader == null) {
            throw new SyntaxException(parameters.get(4).getToken(), "\u9519\u8bef\u7684\u7ed3\u679c\u7edf\u8ba1\u65b9\u5f0f\uff1a" + mode);
        }
        return reader;
    }

    static {
        readerMap.put("SUM", new SumReader());
        readerMap.put("FIRST", new FirstReader());
        readerMap.put("LAST", new LastReader());
        readerMap.put("MAX", new MaxReader());
        readerMap.put("MIN", new MinReader());
        readerMap.put("AVG", new AvgReader());
        readerMap.put("AVERAGE", new AvgReader());
        readerMap.put("COUNT", new CountReader());
    }

    private static final class CountReader
    extends ValueReader {
        private CountReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            if (rows.isEmpty()) {
                return 0;
            }
            Column column = dataSet.getMetadata().getColumn(valIndex);
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.GENERAL_DIM || ((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.TIME_DIM) {
                HashSet<Object> values = new HashSet<Object>();
                for (int row : rows) {
                    BIDataRow record = dataSet.get(row);
                    Object value = record.getValue(valIndex);
                    if (value == null) continue;
                    values.add(value);
                }
                return values.size();
            }
            int count = 0;
            for (int row : rows) {
                BIDataRow record = dataSet.get(row);
                Object value = record.getValue(valIndex);
                if (value == null) continue;
                ++count;
            }
            return count;
        }
    }

    private static final class AvgReader
    extends ValueReader {
        private AvgReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            if (rows.isEmpty()) {
                return null;
            }
            boolean isNull = true;
            double sum = 0.0;
            for (int row : rows) {
                BIDataRow record = dataSet.get(row);
                Object value = record.getValue(valIndex);
                if (value == null) continue;
                isNull = false;
                sum += ((Number)value).doubleValue();
            }
            return isNull ? null : Double.valueOf(sum / (double)rows.size());
        }
    }

    private static final class MinReader
    extends ValueReader {
        private MinReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            Object min = null;
            for (int row : rows) {
                BIDataRow record = dataSet.get(row);
                Object value = record.getValue(valIndex);
                if (value == null || min != null && ((Comparable)value).compareTo(min) >= 0) continue;
                min = value;
            }
            return min;
        }
    }

    private static final class MaxReader
    extends ValueReader {
        private MaxReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            Object max = null;
            for (int row : rows) {
                BIDataRow record = dataSet.get(row);
                Object value = record.getValue(valIndex);
                if (value == null || max != null && ((Comparable)value).compareTo(max) <= 0) continue;
                max = value;
            }
            return max;
        }
    }

    private static final class LastReader
    extends ValueReader {
        private LastReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            for (int i = rows.size() - 1; i >= 0; --i) {
                BIDataRow record = dataSet.get(rows.get(i).intValue());
                Object value = record.getValue(valIndex);
                if (value == null) continue;
                return value;
            }
            return null;
        }
    }

    private static final class FirstReader
    extends ValueReader {
        private FirstReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            for (int row : rows) {
                BIDataRow record = dataSet.get(row);
                Object value = record.getValue(valIndex);
                if (value == null) continue;
                return value;
            }
            return null;
        }
    }

    private static final class SumReader
    extends ValueReader {
        private SumReader() {
        }

        @Override
        public Object read(BIDataSet dataSet, List<Integer> rows, int valIndex) throws SyntaxException {
            boolean isNull = true;
            double sum = 0.0;
            for (int row : rows) {
                BIDataRow record = dataSet.get(row);
                Object value = record.getValue(valIndex);
                if (value == null) continue;
                isNull = false;
                sum += ((Number)value).doubleValue();
            }
            return isNull ? null : Double.valueOf(sum);
        }
    }

    private static abstract class ValueReader {
        private ValueReader() {
        }

        public abstract Object read(BIDataSet var1, List<Integer> var2, int var3) throws SyntaxException;
    }
}

