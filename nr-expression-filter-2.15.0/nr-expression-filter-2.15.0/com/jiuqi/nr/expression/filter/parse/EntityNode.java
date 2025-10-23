/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.expression.filter.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.expression.filter.parse.FilterExecuteContext;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.util.Assert;

class EntityNode
extends DynamicNode {
    private static final long serialVersionUID = -2538456058574741849L;
    private final String fieldName;
    private final int fieldType;
    private boolean isMultiChoose;

    public EntityNode(Token token, String fieldName, int fieldType) {
        super(token);
        Assert.hasText(fieldName, "\u2018fieldName\u2019\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u3002");
        this.fieldName = fieldName.toLowerCase();
        this.fieldType = fieldType;
    }

    public EntityNode(Token token, String fieldName, int fieldType, boolean isMultiChoose) {
        super(token);
        Assert.hasText(fieldName, "\u2018fieldName\u2019\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u3002");
        this.fieldName = fieldName.toLowerCase();
        this.fieldType = fieldType;
        this.isMultiChoose = isMultiChoose;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.isMultiChoose ? 11 : this.fieldType;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        Assert.isInstanceOf(FilterExecuteContext.class, (Object)context, "\u65e0\u6cd5\u8bc6\u522b\u4e0a\u4e0b\u6587\u3002");
        if (this.isMultiChoose) {
            Object value = ((AbstractMap)((FilterExecuteContext)context).getData()).get(this.fieldName);
            if (value == null || "".equals(value)) {
                return null;
            }
            if (value instanceof List) {
                return String.join((CharSequence)";", (List)value);
            }
            return value;
        }
        Object val = ((AbstractMap)((FilterExecuteContext)context).getData()).get(this.fieldName);
        if (this.fieldType == 1) {
            if (val instanceof BigDecimal) {
                return ((BigDecimal)val).intValue() == 1;
            }
            if (val instanceof Integer) {
                return Integer.parseInt(val.toString()) == 1;
            }
            if (val instanceof String) {
                return "1".equals(val.toString());
            }
        }
        if (val instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)val);
            return calendar;
        }
        return val;
    }

    public void toString(StringBuilder buffer) {
        buffer.append("EntityNode{").append("fieldName='").append(this.fieldName).append('\'').append(", fieldType=").append(this.fieldType).append('}');
    }
}

