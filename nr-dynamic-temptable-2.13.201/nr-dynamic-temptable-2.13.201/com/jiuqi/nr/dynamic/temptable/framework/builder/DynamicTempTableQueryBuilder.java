/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.framework.builder;

import com.jiuqi.nr.dynamic.temptable.common.ArgumentCheckUtils;
import com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum;
import com.jiuqi.nr.dynamic.temptable.common.PropertiesUtils;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DynamicTempTableQueryBuilder {
    private final Set<Integer> columnCountSet = new HashSet<Integer>();
    private int startColumnCount = -1;
    private int endColumnCount = -1;
    private DynamicTempTableStatusEnum status = DynamicTempTableStatusEnum.All;
    private final Map<String, String> orderColumns = new LinkedHashMap<String, String>();
    private Iterator<Integer> columnCountIterator;
    private Iterator<Map.Entry<String, String>> orderColumnIterator;

    public DynamicTempTableQueryBuilder addQueryColumnCount(int columnCount) throws IllegalArgumentException {
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(columnCount, "columnCount");
        this.columnCountSet.add(columnCount);
        return this;
    }

    public DynamicTempTableQueryBuilder addQueryColumnCountByStartStop(int startColumnCount, int endColumnCount) throws IllegalArgumentException {
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(startColumnCount, "startColumnCount");
        ArgumentCheckUtils.checkDynamicTempTableColumnCount(endColumnCount, "endColumnCount");
        this.startColumnCount = startColumnCount;
        this.endColumnCount = endColumnCount;
        return this;
    }

    public DynamicTempTableQueryBuilder setQueryStatus(DynamicTempTableStatusEnum status) {
        this.status = status;
        return this;
    }

    public DynamicTempTableQueryBuilder orderByColumnCount(boolean isDesc) {
        this.orderColumns.put("COLUMN_COUNT", isDesc ? "DESC" : "ASC");
        return this;
    }

    public DynamicTempTableQueryBuilder orderByCreateTime(boolean isDesc) {
        this.orderColumns.put("CREATE_TIME", isDesc ? "DESC" : "ASC");
        return this;
    }

    public DynamicTempTableQueryBuilder orderByAcquireTime(boolean isDesc) {
        this.orderColumns.put("ACQUIRE_TIME", isDesc ? "DESC" : "ASC");
        return this;
    }

    public DynamicTempTableQueryBuilder orderByLastUseTime(boolean isDesc) {
        this.orderColumns.put("LAST_USE_TIME", isDesc ? "DESC" : "ASC");
        return this;
    }

    public ITableQueryInfo build() {
        return new TableQueryInfo();
    }

    private class TableQueryInfo
    implements ITableQueryInfo {
        private TableQueryInfo() {
        }

        @Override
        public Iterator<Integer> ColumnCountIterator() {
            if (DynamicTempTableQueryBuilder.this.columnCountIterator == null) {
                if (DynamicTempTableQueryBuilder.this.startColumnCount != -1 || DynamicTempTableQueryBuilder.this.endColumnCount != -1) {
                    DynamicTempTableQueryBuilder.this.startColumnCount = DynamicTempTableQueryBuilder.this.startColumnCount == -1 ? 1 : DynamicTempTableQueryBuilder.this.startColumnCount;
                    DynamicTempTableQueryBuilder.this.endColumnCount = DynamicTempTableQueryBuilder.this.endColumnCount == -1 ? PropertiesUtils.getIntPropertyValue("max-column-number") : DynamicTempTableQueryBuilder.this.endColumnCount;
                    for (int i = DynamicTempTableQueryBuilder.this.startColumnCount; i <= DynamicTempTableQueryBuilder.this.endColumnCount; ++i) {
                        DynamicTempTableQueryBuilder.this.columnCountSet.add(i);
                    }
                }
                DynamicTempTableQueryBuilder.this.columnCountIterator = DynamicTempTableQueryBuilder.this.columnCountSet.iterator();
            }
            return DynamicTempTableQueryBuilder.this.columnCountIterator;
        }

        @Override
        public DynamicTempTableStatusEnum getQueryStatus() {
            return DynamicTempTableQueryBuilder.this.status;
        }

        @Override
        public Iterator<Map.Entry<String, String>> orderColumnIterator() {
            if (DynamicTempTableQueryBuilder.this.orderColumnIterator == null) {
                DynamicTempTableQueryBuilder.this.orderColumnIterator = DynamicTempTableQueryBuilder.this.orderColumns.entrySet().iterator();
            }
            return DynamicTempTableQueryBuilder.this.orderColumnIterator;
        }
    }
}

