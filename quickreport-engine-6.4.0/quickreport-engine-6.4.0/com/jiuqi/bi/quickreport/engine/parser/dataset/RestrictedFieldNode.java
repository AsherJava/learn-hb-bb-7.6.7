/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public final class RestrictedFieldNode
extends DynamicNode {
    private static final long serialVersionUID = 1L;
    private DSModel dataset;
    private DSField field;
    private String tag;
    private boolean fullName;

    public RestrictedFieldNode(Token token, DSModel dataset, DSField field, String tag, boolean fullName) {
        super(token);
        this.dataset = dataset;
        this.field = field;
        this.tag = tag;
        this.fullName = fullName;
    }

    public int getType(IContext context) throws SyntaxException {
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        throw new SyntaxException(this.getToken(), "\u65e0\u6cd5\u53c2\u4e0e\u8ba1\u7b97\u3002");
    }

    public void toString(StringBuilder buffer) {
        if (this.fullName) {
            buffer.append(this.dataset.getName()).append('.');
        }
        buffer.append(this.field.getName()).append('.').append(this.tag);
    }

    public boolean isStatic(IContext context) {
        return true;
    }

    public DSModel getDataSet() {
        return this.dataset;
    }

    public DSField getField() {
        return this.field;
    }

    public String getTag() {
        return this.tag;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (this.fullName) {
            buffer.append(this.dataset.getName()).append('.');
        }
        buffer.append(this.field.getName()).append('.').append(this.tag);
    }
}

