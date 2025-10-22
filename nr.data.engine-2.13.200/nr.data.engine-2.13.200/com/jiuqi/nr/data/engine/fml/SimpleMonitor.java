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
package com.jiuqi.nr.data.engine.fml;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.execption.ExpressionJudgeException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleMonitor
extends AbstractMonitor {
    private Set<String> erorrExpKeys = new HashSet<String>();
    private List<Exception> exceptions = new ArrayList<Exception>();

    public void exception(Exception e) {
        throw new ExpressionJudgeException(e.getMessage(), e);
    }

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        this.erorrExpKeys.add(expression.getSource().getId());
    }

    public Set<String> getErorrExpKeys() {
        return this.erorrExpKeys;
    }

    public boolean hasError() {
        return this.erorrExpKeys.size() > 0;
    }

    public List<Exception> getExceptions() {
        return this.exceptions;
    }
}

