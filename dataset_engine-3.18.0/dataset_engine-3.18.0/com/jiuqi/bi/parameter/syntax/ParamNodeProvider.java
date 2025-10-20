/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.parameter.syntax;

import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.syntax.ParamInfoNode;
import com.jiuqi.bi.parameter.syntax.ParamNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class ParamNodeProvider
implements IDynamicNodeProvider {
    protected final IParameterEnv paramEnv;

    public ParamNodeProvider(IParameterEnv paramEnv) {
        this.paramEnv = paramEnv;
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        ParameterModel model = this.paramEnv.getParameterModelByName(refName);
        if (model == null) {
            return null;
        }
        if (model.isZBParameter()) {
            return this.findAsZBParameter(context, token, model);
        }
        return new ParamNode(token, this.paramEnv, model);
    }

    private IASTNode findAsZBParameter(IContext context, Token token, ParameterModel model) throws DynamicNodeException {
        String zbName = this.getZBParamValue(model);
        return this.findZBNode(context, token, zbName, model);
    }

    private String getZBParamValue(ParameterModel model) throws DynamicNodeException {
        List<?> retVal;
        try {
            retVal = this.paramEnv.getValueAsList(model.getName());
        }
        catch (ParameterException e) {
            throw new DynamicNodeException((Throwable)e);
        }
        if (retVal == null || retVal.isEmpty()) {
            throw new DynamicNodeException("\u6307\u6807\u53c2\u6570[" + model.getName() + "]\u7684\u503c\u672a\u6307\u5b9a\u3002");
        }
        return (String)retVal.get(0);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        int size = objPath.size();
        if (size < 2 && size > 4) {
            return null;
        }
        if (size == 2) {
            if ("MAX".equalsIgnoreCase(objPath.get(1)) || "MIN".equalsIgnoreCase(objPath.get(1))) {
                return this.findRangeParam(context, token, objPath.get(0), objPath.get(1));
            }
            if ("CODE".equalsIgnoreCase(objPath.get(1)) || "TITLE".equalsIgnoreCase(objPath.get(1))) {
                return this.findParamInfo(context, token, objPath.get(0), objPath.get(1));
            }
            return null;
        }
        if (size == 3) {
            return this.findAdhocParam(context, token);
        }
        return this.findAdhocParamInfo(context, token, new StringBuffer().append(objPath.get(0)).append(".").append(objPath.get(1)).append(".").append(objPath.get(2)).toString(), objPath.get(3));
    }

    private IASTNode findAdhocParamInfo(IContext context, Token token, String paramName, String suffix) {
        ParameterModel paramModel = this.paramEnv.getParameterModelByName(paramName);
        if (paramModel == null) {
            return null;
        }
        if ("CODE".equalsIgnoreCase(suffix) || "TITLE".equalsIgnoreCase(suffix)) {
            ParamInfoNode node = new ParamInfoNode(token, this.paramEnv, paramModel);
            node.setSuffix(suffix);
            return node;
        }
        if ("MAX".equalsIgnoreCase(suffix) || "MIN".equalsIgnoreCase(suffix)) {
            ParamNode node = new ParamNode(token, this.paramEnv, paramModel);
            node.setSuffix(suffix);
            return node;
        }
        return null;
    }

    private IASTNode findAdhocParam(IContext context, Token token) {
        ParameterModel paramModel = this.paramEnv.getParameterModelByName(token.text());
        if (paramModel != null) {
            return new ParamNode(token, this.paramEnv, paramModel);
        }
        return null;
    }

    private IASTNode findParamInfo(IContext context, Token token, String paramName, String suffix) {
        ParameterModel paramModel = this.paramEnv.getParameterModelByName(paramName);
        if (paramModel == null || !paramModel.isZBParameter()) {
            return null;
        }
        ParamInfoNode node = new ParamInfoNode(token, this.paramEnv, paramModel);
        node.setSuffix(suffix);
        return node;
    }

    private IASTNode findRangeParam(IContext context, Token token, String paramName, String suffix) throws DynamicNodeException {
        IASTNode node = this.find(context, token, paramName);
        if (!(node instanceof ParamNode)) {
            return null;
        }
        ((ParamNode)node).setSuffix(suffix);
        return node;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        ParameterModel model = this.paramEnv.getParameterModelByName(refName);
        if (model == null || !model.isZBParameter()) {
            return null;
        }
        String zbName = this.getZBParamValue(model);
        return this.findZBNode(context, token, zbName, spec, model);
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        if (objPath.size() != 1) {
            return null;
        }
        ParameterModel model = this.paramEnv.getParameterModelByName(objPath.get(0));
        if (model == null || !model.isZBParameter()) {
            return null;
        }
        String zbName = this.getZBParamValue(model);
        return this.findZBNode(context, token, zbName, restrictItems, model);
    }

    protected IASTNode findZBNode(IContext context, Token token, String zbName, ParameterModel model) throws DynamicNodeException {
        return new ParamNode(token, this.paramEnv, model);
    }

    protected IASTNode findZBNode(IContext context, Token token, String zbName, String spec, ParameterModel model) throws DynamicNodeException {
        throw new DynamicNodeException("\u5c1a\u672a\u652f\u6301\u6307\u6807\u53c2\u6570\u7684\u9650\u5b9a\u5904\u7406\u3002");
    }

    protected IASTNode findZBNode(IContext context, Token token, String zbName, List<IASTNode> restrictItems, ParameterModel model) throws DynamicNodeException {
        throw new DynamicNodeException("\u5c1a\u672a\u652f\u6301\u6307\u6807\u53c2\u6570\u7684\u9650\u5b9a\u5904\u7406\u3002");
    }
}

