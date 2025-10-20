/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.parameter.syntax;

import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.syntax.ParamNode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;

public abstract class ParamFilterTranslator {
    public void toSQL(IContext context, StringBuilder buffer, IASTNode valueNode, ParamNode paramNode, ISQLInfo info) throws InterpretException {
        Object paramValue;
        try {
            paramValue = paramNode.getParamValue(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        if (paramValue == null) {
            this.toNullValueSQL(context, buffer, valueNode, info);
        } else if (paramValue instanceof SmartSelector) {
            this.toSmartValueSQL(context, buffer, valueNode, (SmartSelector)paramValue, info);
        } else if (paramValue instanceof ArrayData) {
            if (paramNode.getParam().isRangeParameter()) {
                this.toRangeValueSQL(context, buffer, valueNode, (ArrayData)paramValue, info);
            } else {
                this.toArrayValueSQL(context, buffer, valueNode, (ArrayData)paramValue, info);
            }
        } else {
            this.toSimpleValueSQL(context, buffer, valueNode, paramValue, info);
        }
    }

    public abstract String toValueFieldSQL(IContext var1, IASTNode var2, ISQLInfo var3) throws InterpretException;

    private void toNullValueSQL(IContext context, StringBuilder buffer, IASTNode valueNode, ISQLInfo info) throws InterpretException {
        this.toValueFieldSQL(context, buffer, valueNode, info);
        buffer.append(" IS NULL");
    }

    private void toSimpleValueSQL(IContext context, StringBuilder buffer, IASTNode valueNode, Object paramValue, ISQLInfo info) throws InterpretException {
        this.toValueFieldSQL(context, buffer, valueNode, info);
        buffer.append('=');
        DataNode.toSQL((StringBuilder)buffer, (int)DataType.typeOf((Object)paramValue), (Object)paramValue, (ISQLInfo)info);
    }

    private void toArrayValueSQL(IContext context, StringBuilder buffer, IASTNode valueNode, ArrayData arr, ISQLInfo info) throws InterpretException {
        this.toValueFieldSQL(context, buffer, valueNode, info);
        buffer.append(" IN ");
        DataNode.toSQL((StringBuilder)buffer, (int)11, (Object)arr, (ISQLInfo)info);
    }

    private void toRangeValueSQL(IContext context, StringBuilder buffer, IASTNode valueNode, ArrayData rangeVal, ISQLInfo info) throws InterpretException {
        Object min = rangeVal.get(0);
        Object max = rangeVal.get(1);
        if (min == null && max == null) {
            buffer.append("1=1");
        } else if (min != null && max != null) {
            buffer.append('(');
            this.toValueFieldSQL(context, buffer, valueNode, info);
            buffer.append(">=");
            DataNode.toSQL((StringBuilder)buffer, (int)rangeVal.baseType(), (Object)min, (ISQLInfo)info);
            buffer.append(" AND ");
            this.toValueFieldSQL(context, buffer, valueNode, info);
            buffer.append("<=");
            DataNode.toSQL((StringBuilder)buffer, (int)rangeVal.baseType(), (Object)max, (ISQLInfo)info);
            buffer.append(')');
        } else if (min != null) {
            this.toValueFieldSQL(context, buffer, valueNode, info);
            buffer.append(">=");
            DataNode.toSQL((StringBuilder)buffer, (int)rangeVal.baseType(), (Object)min, (ISQLInfo)info);
        } else {
            this.toValueFieldSQL(context, buffer, valueNode, info);
            buffer.append("<=");
            DataNode.toSQL((StringBuilder)buffer, (int)rangeVal.baseType(), (Object)max, (ISQLInfo)info);
        }
    }

    private void toSmartValueSQL(IContext context, StringBuilder buffer, IASTNode valueNode, SmartSelector paramValue, ISQLInfo info) throws InterpretException {
        int valueType;
        try {
            valueType = valueNode.getType(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        String valueField = this.toValueFieldSQL(context, valueNode, info);
        String filter = paramValue.toSQL(valueField, valueType);
        buffer.append("(").append(filter).append(")");
    }

    private void toValueFieldSQL(IContext context, StringBuilder buffer, IASTNode valueNode, ISQLInfo info) throws InterpretException {
        if (valueNode.childrenSize() > 0) {
            buffer.append('(');
        }
        valueNode.interpret(context, buffer, Language.SQL, (Object)info);
        if (valueNode.childrenSize() > 0) {
            buffer.append(')');
        }
    }
}

