/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNode
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParamNodeWithoutEnvProvider
implements IDynamicNodeProvider {
    private Map<String, ParameterModel> finder = new HashMap<String, ParameterModel>();
    private IParameterEnv paramEnv;

    public ParamNodeWithoutEnvProvider(List<ParameterModel> params) {
        for (ParameterModel p : params) {
            this.finder.put(p.getName().toUpperCase(), p);
        }
        this.paramEnv = new ParameterEnv("97979797000000000000000000000000", params);
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        ParameterModel model = this.finder.get(refName.toUpperCase());
        if (model == null) {
            return null;
        }
        return new ParamNode(token, this.paramEnv, model);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        if (objPath.size() != 2) {
            return null;
        }
        if (!"MAX".equalsIgnoreCase(objPath.get(1)) && !"MIN".equalsIgnoreCase(objPath.get(1))) {
            return null;
        }
        IASTNode node = this.find(context, token, objPath.get(0));
        if (!(node instanceof ParamNode)) {
            return null;
        }
        ((ParamNode)node).setSuffix(objPath.get(1));
        return node;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }
}

