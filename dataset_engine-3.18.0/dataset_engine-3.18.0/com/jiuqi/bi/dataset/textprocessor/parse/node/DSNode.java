/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.node;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IDSNodeDescriptor;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class DSNode
extends DynamicNode
implements IDSNodeDescriptor {
    private DSModel dsModel;

    public DSNode(Token token, DSModel dsModel) {
        super(token);
        this.dsModel = dsModel;
    }

    public int getType(IContext context) throws SyntaxException {
        return 5100;
    }

    public String getDSName() {
        return this.dsModel.getName();
    }

    @Override
    public DSModel getDSModel() {
        return this.dsModel;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        try {
            return helper.openDataset(this.dsModel);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.dsModel.getName());
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.dsModel.getName());
    }
}

