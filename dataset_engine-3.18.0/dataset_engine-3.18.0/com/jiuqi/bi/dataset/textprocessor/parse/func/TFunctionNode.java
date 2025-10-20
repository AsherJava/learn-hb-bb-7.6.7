/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func;

import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFunctionNode;
import com.jiuqi.bi.dataset.function.DSFunctionProvider;
import com.jiuqi.bi.dataset.function.Lag;
import com.jiuqi.bi.dataset.function.RowNum;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.DS_LAG;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.LAG;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IAdjustable;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IDSNodeDescriptor;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class TFunctionNode
extends FunctionNode
implements IAdjustable,
IDSNodeDescriptor {
    public TFunctionNode(Token token, IFunction function, List<IASTNode> params) {
        super(token, function, params);
    }

    public int validate(IContext context) throws SyntaxException {
        DSModel dsModel = this.getDSModel();
        if (dsModel != null) {
            for (IASTNode param : this.parameters) {
                this.checkField(dsModel, param);
            }
        }
        return super.validate(context);
    }

    public int getType(IContext context) throws SyntaxException {
        DSModel dsModel = this.getDSModel();
        if (dsModel != null) {
            for (IASTNode param : this.parameters) {
                this.checkField(dsModel, param);
            }
        }
        return super.getType(context);
    }

    @Override
    public DSModel getDSModel() {
        if (this.getDefine() instanceof TFunction) {
            TFunction tf = (TFunction)this.getDefine();
            for (int pIdx = 0; pIdx < this.parameters.size(); ++pIdx) {
                DSNode dsNode;
                if (!tf.isDatasetNodeParam(pIdx) || (dsNode = this.searchDSNode((IASTNode)this.parameters.get(pIdx))) == null) continue;
                return dsNode.getDSModel();
            }
        }
        return null;
    }

    private void checkField(DSModel dsModel, IASTNode root) throws SyntaxException {
        TFunctionNode tnode;
        if (root instanceof TFunctionNode && (tnode = (TFunctionNode)root).isAdjustable(dsModel.getName())) {
            root = tnode.adjust();
        }
        for (IASTNode node : root) {
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode fieldNode = (DSFieldNode)node;
            DSField fd = this.find(dsModel, fieldNode.getName());
            if (fd == null) {
                throw new SyntaxException("\u6570\u636e\u96c6" + dsModel.getName() + "\u4e2d\u4e0d\u5b58\u5728\u5b57\u6bb5" + fieldNode.getName());
            }
            fieldNode.getFieldInfo().loadFromDSField(fd);
        }
    }

    private DSField find(DSModel dsModel, String fieldName) {
        List<DSField> fields = dsModel.getFields();
        for (DSField field : fields) {
            if (!field.getName().equalsIgnoreCase(fieldName)) continue;
            return field;
        }
        return null;
    }

    private DSNode searchDSNode(IASTNode node) {
        for (IASTNode nd : node) {
            if (!(nd instanceof DSNode)) continue;
            return (DSNode)nd;
        }
        return null;
    }

    @Override
    public boolean isAdjustable(String dsName) {
        if (this.function instanceof RowNum) {
            return true;
        }
        return this.function instanceof DS_LAG || this.function instanceof LAG;
    }

    @Override
    public IASTNode adjust() {
        if (this.function instanceof RowNum) {
            return new DSFunctionNode(this.token, this.function, this.parameters);
        }
        if (this.function instanceof LAG) {
            return new DSFunctionNode(this.token, (IFunction)new Lag(), this.parameters);
        }
        if (this.function instanceof DS_LAG) {
            return new DSFunctionNode(this.token, (IFunction)new Lag(), this.parameters.subList(1, this.parameters.size()));
        }
        return null;
    }

    public IASTNode transformToDsNode() {
        try {
            IFunction func = DSFunctionProvider.DS_PROVIDER.find(null, this.function.name());
            if (func != null) {
                ArrayList<IASTNode> params = new ArrayList<IASTNode>();
                TFunction tf = (TFunction)this.function;
                for (int i = 0; i < this.parameters.size(); ++i) {
                    if (tf.isDatasetNodeParam(i)) continue;
                    params.add((IASTNode)this.parameters.get(i));
                }
                return new DSFunctionNode(this.token, func, params);
            }
        }
        catch (FunctionException e) {
            return null;
        }
        return null;
    }
}

