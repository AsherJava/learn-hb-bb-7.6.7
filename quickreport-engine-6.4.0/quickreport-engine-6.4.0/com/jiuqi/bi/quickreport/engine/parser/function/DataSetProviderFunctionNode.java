/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.IFunction
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.IFunction;
import java.util.List;

public final class DataSetProviderFunctionNode
extends DataSetFunctionNode
implements IDataSetNode {
    private static final long serialVersionUID = 1841596707696815070L;
    private DSModel model;

    public DataSetProviderFunctionNode(Token token, IFunction function, IASTNode[] params) {
        super(token, function, params);
    }

    public DataSetProviderFunctionNode(Token token, IFunction function, List<IASTNode> params) {
        super(token, function, params);
    }

    public DataSetProviderFunctionNode(Token token, IFunction function) {
        super(token, function);
    }

    @Override
    public DSModel getDataSetModel() {
        if (this.childrenSize() > 0) {
            IDataSetNode dsNode = (IDataSetNode)this.getChild(0);
            return dsNode.getDataSetModel();
        }
        return this.model;
    }

    public void setDataSetModel(DSModel model) {
        this.model = model;
    }
}

