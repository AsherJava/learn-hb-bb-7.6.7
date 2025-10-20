/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.organization.filter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilterContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.util.Assert;

class BiSyntaxOrganizationNode
extends DynamicNode {
    private static final long serialVersionUID = -2538456058574741849L;
    private final String fieldName;
    private final int fieldType;
    private boolean isMultiChoose;

    public BiSyntaxOrganizationNode(Token token, String fieldName, int fieldType) {
        super(token);
        Assert.hasText(fieldName, "\u2018fieldName\u2019\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u7a0b\u5e8f\u3002");
        this.fieldName = fieldName.toLowerCase();
        this.fieldType = fieldType;
    }

    public BiSyntaxOrganizationNode(Token token, String fieldName, int fieldType, boolean isMultiChoose) {
        super(token);
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.isMultiChoose = isMultiChoose;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.isMultiChoose ? 11 : this.fieldType;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        Assert.isInstanceOf(BiSyntaxOrganizationFilterContext.class, (Object)context, "\u65e0\u6cd5\u8bc6\u522b\u4e0a\u4e0b\u6587\u3002");
        if (this.isMultiChoose) {
            Object value = ((BiSyntaxOrganizationFilterContext)context).getData().get((Object)this.fieldName);
            if (value == null || "".equals(value)) {
                return new ArrayData(this.fieldType, Collections.emptyList());
            }
            return new ArrayData(this.fieldType, (Collection)((ArrayList)value));
        }
        Object val = ((BiSyntaxOrganizationFilterContext)context).getData().get((Object)this.fieldName);
        if (1 == this.fieldType) {
            if (val == null) {
                return false;
            }
            return ((Number)val).intValue() != 0;
        }
        return val;
    }

    public void toString(StringBuilder buffer) {
    }
}

