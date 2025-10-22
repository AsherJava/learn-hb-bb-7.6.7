/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.HashSet;
import java.util.Set;

public class FmlConditionMonitor
extends AbstractMonitor {
    private final Set<String> errorConditionKeys = new HashSet<String>();

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        this.errorConditionKeys.add(expression.getKey());
    }

    public Set<String> getErrorConditionKeys() {
        return this.errorConditionKeys;
    }
}

