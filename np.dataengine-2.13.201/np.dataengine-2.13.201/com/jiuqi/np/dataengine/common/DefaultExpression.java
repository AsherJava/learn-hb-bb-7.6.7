/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.definition.facade.FieldDefine;

public class DefaultExpression {
    private final FieldDefine fieldDefine;
    private final IExpression expression;

    public DefaultExpression(FieldDefine fieldDefine, IExpression expression) {
        this.fieldDefine = fieldDefine;
        this.expression = expression;
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public IExpression getExpression() {
        return this.expression;
    }
}

