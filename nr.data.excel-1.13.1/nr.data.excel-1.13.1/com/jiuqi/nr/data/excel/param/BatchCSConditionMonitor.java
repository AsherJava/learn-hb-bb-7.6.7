/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.IDataChangeListener
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BatchCSConditionMonitor
extends AbstractMonitor {
    private Map<String, Set<DimensionValueSet>> result = new HashMap<String, Set<DimensionValueSet>>();

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        String id = expression.getSource().getId();
        DimensionValueSet rowKey = context.getRowKey();
        if (rowKey != null) {
            if (this.result.containsKey(id)) {
                Set<DimensionValueSet> set = this.result.get(id);
                set.add(rowKey);
            } else {
                HashSet<DimensionValueSet> r = new HashSet<DimensionValueSet>();
                r.add(rowKey);
                this.result.put(id, r);
            }
        }
    }

    public void finish() {
        if (!this.finished) {
            for (IDataChangeListener dataChangeListener : this.getDataChangeListeners()) {
                dataChangeListener.finish();
            }
            this.onProgress(1.0);
        }
        this.finished = true;
    }

    public Map<String, Set<DimensionValueSet>> getResult() {
        return this.result;
    }
}

