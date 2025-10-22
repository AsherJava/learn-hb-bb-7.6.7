/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnitChekMonitor
extends AbstractMonitor {
    private DimensionValueSet srcDimension;
    private Set<Object> filteredKeys = new HashSet<Object>();
    private String mainDim;

    public UnitChekMonitor(DimensionValueSet srcDimension) {
        this.srcDimension = srcDimension;
    }

    public void setMainDim(String mainDim) {
        this.mainDim = mainDim;
    }

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        DimensionValueSet rowKey = context.getRowKey();
        Object key = rowKey.getValue(this.mainDim);
        if (key != null) {
            this.filteredKeys.add(key);
        }
    }

    public void finish() {
        super.finish();
        List keys = (List)this.srcDimension.getValue(this.mainDim);
        ArrayList resultList = new ArrayList();
        if (keys != null) {
            for (Object value : keys) {
                if (this.filteredKeys.contains(value)) continue;
                resultList.add(value);
            }
        }
        this.srcDimension.setValue(this.mainDim, resultList);
    }

    public Set<Object> getFilteredKeys() {
        return this.filteredKeys;
    }

    public DimensionValueSet getSrcDimension() {
        return this.srcDimension;
    }

    public void setSrcDimension(DimensionValueSet srcDimension) {
        this.srcDimension = srcDimension;
    }
}

