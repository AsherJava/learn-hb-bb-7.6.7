/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func.extract;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSetQueryResult {
    private String dataSetName;
    private String paraValues;
    private BIDataSet dataSet;
    private IReportFunction function;
    private Map<String, Map<String, Integer>> searchIndexes = new HashMap<String, Map<String, Integer>>();

    public DataSetQueryResult(String dataSetName, BIDataSet dataSet, String paraValues, IReportFunction function) {
        this.dataSetName = dataSetName;
        this.paraValues = paraValues;
        this.dataSet = dataSet;
        this.function = function;
    }

    public Metadata<BIDataSetFieldInfo> getMetadata() {
        return this.dataSet.getMetadata();
    }

    public Object getValue(QueryContext qContext, String columnName) throws Exception {
        return this.getValue(qContext, null, columnName);
    }

    public Object getValue(QueryContext qContext, String rowID, String columnName) throws Exception {
        String seachIndexName;
        Map<String, Integer> searchIndex;
        Integer rowIndex;
        IMonitor monitor = qContext.getMonitor();
        if (this.dataSet.getRecordCount() <= 0) {
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u6570\u636e\u96c6\u672a\u67e5\u5230\u6570\u636e", (Object)this.function);
            }
            return null;
        }
        if (monitor != null && monitor.isDebug()) {
            monitor.message("\u6570\u636e\u96c6\u5b57\u6bb5\uff1a" + columnName, (Object)this.function);
        }
        int columnIndex = this.indexOf(columnName);
        if (StringUtils.isEmpty((String)rowID)) {
            BIDataRow biDataRow = this.dataSet.get(0);
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u9ed8\u8ba4\u5b9a\u4f4d\u5230\u7b2c1\u884c\uff1a" + biDataRow.toString(), (Object)this.function);
            }
            return biDataRow.getValue(columnIndex);
        }
        String[] keyColumns = rowID.split(";");
        ArrayList<Integer> keyIndexes = new ArrayList<Integer>();
        StringBuilder keyBuff = new StringBuilder();
        StringBuilder seachIndexBuff = new StringBuilder();
        for (String keyColumn : keyColumns) {
            String[] keyStr = keyColumn.split("=");
            if (keyStr.length != 2) {
                throw new SyntaxException("\u65e0\u6548\u7684\u952e\u503c\uff1a" + keyColumn);
            }
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u5173\u952e\u5217\u53d6\u503c\uff1a" + keyColumn, (Object)this.function);
            }
            String keyName = keyStr[0];
            String keyValue = keyStr[1];
            int keyIndex = this.dataSet.getMetadata().indexOf(keyName);
            if (keyIndex < 0) {
                throw new SyntaxException("\u65e0\u6548\u7684\u6570\u636e\u96c6\u5217\u540d\uff1a" + keyName);
            }
            keyIndexes.add(keyIndex);
            seachIndexBuff.append(keyName).append(";");
            keyBuff.append(keyValue).append(";");
        }
        if (seachIndexBuff.length() > 0) {
            seachIndexBuff.setLength(seachIndexBuff.length() - 1);
            keyBuff.setLength(keyBuff.length() - 1);
        }
        if ((rowIndex = (searchIndex = this.getSearchIndex(keyIndexes, seachIndexName = seachIndexBuff.toString())).get(keyBuff.toString())) != null) {
            BIDataRow biDataRow = this.dataSet.get(rowIndex.intValue());
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u5b9a\u4f4d\u5230\u7b2c" + (rowIndex + 1) + "\u884c\uff1a" + biDataRow.toString(), (Object)this.function);
            }
            return biDataRow.getValue(columnIndex);
        }
        return null;
    }

    public BIDataRow getRow(int index) {
        return this.dataSet.get(index);
    }

    public int size() {
        return this.dataSet.getRecordCount();
    }

    private Map<String, Integer> getSearchIndex(List<Integer> keyIndexes, String seachIndexName) {
        Map<String, Integer> searchIndex = this.searchIndexes.get(seachIndexName);
        if (searchIndex == null) {
            searchIndex = new HashMap<String, Integer>();
            this.searchIndexes.put(seachIndexName, searchIndex);
            for (int rowIndex = 0; rowIndex < this.size(); ++rowIndex) {
                BIDataRow row = this.getRow(rowIndex);
                StringBuilder rowKey = new StringBuilder();
                for (int index : keyIndexes) {
                    rowKey.append(row.getValue(index)).append(";");
                }
                if (rowKey.length() > 0) {
                    rowKey.setLength(rowKey.length() - 1);
                }
                searchIndex.put(rowKey.toString(), rowIndex);
            }
        }
        return searchIndex;
    }

    private int indexOf(String columnName) throws SyntaxException {
        int columnIndex = this.dataSet.getMetadata().indexOf(columnName);
        if (columnIndex < 0) {
            throw new SyntaxException("\u65e0\u6548\u7684\u6570\u636e\u96c6\u5217\u540d\uff1a" + columnName);
        }
        return columnIndex;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("QUERY ").append(this.dataSetName);
        if (StringUtils.isNotEmpty((String)this.paraValues)) {
            buff.append(" WHERE ").append(this.paraValues);
        }
        buff.append("\n");
        buff.append("recordCount=").append(this.dataSet.getRecordCount()).append("\n");
        buff.append(this.dump());
        return buff.toString();
    }

    private String dump() {
        StringWriter writer = new StringWriter();
        for (Column column : this.dataSet.getMetadata()) {
            String name = column.getName();
            if (name.equals("SYS_TIMEKEY") || name.equals("SYS_ROWNUM")) continue;
            if (column.getIndex() > 0) {
                writer.write(",");
            }
            writer.write(this.tryQuoteString(column.getName()));
        }
        writer.write(StringUtils.LINE_SEPARATOR);
        int colNum = this.getMetadata().getColumnCount();
        int writeRowCount = this.dataSet.getRecordCount() > 10 ? 10 : this.dataSet.getRecordCount();
        for (int r = 0; r < writeRowCount; ++r) {
            BIDataRow row = this.dataSet.get(r);
            for (int i = 0; i < colNum; ++i) {
                String name = this.getMetadata().getColumn(i).getName();
                if (name.equals("SYS_TIMEKEY") || name.equals("SYS_ROWNUM")) continue;
                String value = DataType.formatValue((int)0, (Object)row.getValue(i));
                if (i > 0) {
                    writer.write(",");
                }
                writer.write(this.tryQuoteString(value));
            }
            writer.write(StringUtils.LINE_SEPARATOR);
        }
        if (this.dataSet.getRecordCount() > 10) {
            writer.write("more...");
        }
        return writer.toString();
    }

    private String tryQuoteString(String s) {
        if (s == null) {
            return s;
        }
        if (s.indexOf(34) >= 0 || s.indexOf(44) >= 0) {
            return StringUtils.quote((String)s, (char)'\"');
        }
        return s;
    }
}

