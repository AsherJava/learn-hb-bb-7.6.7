/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.intf.FieldNode
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.filter.bill.formula.EnumDataFormulaContext;
import com.jiuqi.va.formula.intf.FieldNode;

public class EnumDataFieldNode
extends DynamicNode
implements FieldNode {
    private static final long serialVersionUID = 1348943564522987149L;
    private int datatype;
    private String title;
    private String val;
    private int type;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVal() {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public EnumDataFieldNode(Token token, int datatype, String title, String val, int type) {
        super(token);
        this.datatype = datatype;
        this.title = title;
        this.val = val;
        this.type = type;
    }

    public EnumDataFieldNode(Token token) {
        super(token);
    }

    public int getType(IContext context) throws SyntaxException {
        return 6;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        EnumDataFormulaContext formulaContext = (EnumDataFormulaContext)context;
        return this.getType() == 1 ? formulaContext.getVal() : formulaContext.getTitle();
    }

    public void toString(StringBuilder buffer) {
    }

    public String getName() {
        return null;
    }

    public void setDataType(int datatype) {
        this.datatype = datatype;
    }

    public int getDataType() {
        return this.datatype;
    }
}

