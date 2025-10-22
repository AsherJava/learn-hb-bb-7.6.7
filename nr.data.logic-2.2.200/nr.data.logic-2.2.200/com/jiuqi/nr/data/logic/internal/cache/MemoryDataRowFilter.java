/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemoryDataRowFilter
implements IDataRowFilter {
    private Map<Integer, Set<String>> valuesMap;
    private StringBuilder rowFilter;
    private MemoryDataContext context;
    private IExpression filterExp;

    public MemoryDataRowFilter(MemoryDataContext context) {
        this.context = context;
    }

    public boolean filter(DataRow row) throws DataSetException {
        if (this.valuesMap != null) {
            for (int columnIndex : this.valuesMap.keySet()) {
                Object value = row.getValue(columnIndex);
                Set<String> valuesSet = this.valuesMap.get(columnIndex);
                if (valuesSet.contains(value.toString())) continue;
                return false;
            }
        }
        if (this.rowFilter != null) {
            try {
                if (this.filterExp == null) {
                    this.filterExp = this.context.getParser().parseCond(this.rowFilter.toString(), (IContext)this.context);
                }
                this.context.setRow(row);
                return this.filterExp.judge((IContext)this.context);
            }
            catch (SyntaxException e) {
                throw new DataSetException(e.getMessage(), (Throwable)e);
            }
        }
        return true;
    }

    public void addValuesFilter(int columnIndex, List<String> values) {
        if (this.valuesMap == null) {
            this.valuesMap = new HashMap<Integer, Set<String>>();
        }
        HashSet<String> valuesSet = new HashSet<String>(values);
        this.valuesMap.put(columnIndex, valuesSet);
    }

    public boolean isEmpty() {
        return this.valuesMap == null && this.rowFilter == null;
    }

    public void appendFilter(String filter) {
        if (this.rowFilter == null) {
            this.rowFilter = new StringBuilder();
        }
        if (this.rowFilter.length() > 0) {
            this.rowFilter.append(" and ");
        }
        this.rowFilter.append("(").append(filter).append(")");
    }

    public void setContext(MemoryDataContext context) {
        this.context = context;
    }
}

