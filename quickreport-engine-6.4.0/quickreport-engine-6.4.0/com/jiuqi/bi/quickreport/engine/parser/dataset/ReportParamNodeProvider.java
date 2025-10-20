/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.context.ReportNotFoundException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSParamFieldNode;
import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import java.util.List;

public class ReportParamNodeProvider
extends ParamNodeProvider {
    public ReportParamNodeProvider(IParameterEnv paramEnv) {
        super(paramEnv);
    }

    protected IASTNode findZBNode(IContext context, Token token, String zbName, ParameterModel model) throws DynamicNodeException {
        int p = zbName.indexOf(46);
        if (p == -1) {
            return this.searchZBNode((ReportContext)context, token, model, zbName);
        }
        String dataSetName = zbName.substring(0, p);
        String fieldName = zbName.substring(p + 1);
        return this.locateZBNode((ReportContext)context, token, model, dataSetName, fieldName);
    }

    private IASTNode searchZBNode(ReportContext context, Token token, ParameterModel model, String fieldName) throws DynamicNodeException {
        DSModel refDataSet = null;
        DSField refField = null;
        for (DataSetInfo dsInfo : context.getReport().getRefDataSets()) {
            DSModel dsModel;
            try {
                dsModel = context.openDataSetModel(dsInfo.getId());
            }
            catch (ReportNotFoundException e) {
                continue;
            }
            catch (ReportContextException e) {
                throw new DynamicNodeException((Throwable)e);
            }
            DSField dsField = dsModel.findField(fieldName);
            if (dsField == null) continue;
            if (refDataSet == null) {
                refDataSet = dsModel;
                refField = dsField;
                continue;
            }
            throw new DynamicNodeException("\u67e5\u627e\u6307\u6807\u53c2\u6570[" + model.getName() + "]\u5bf9\u5e94\u7684\u6570\u636e\u96c6\u5b57\u6bb5[" + fieldName + "]\u65f6\uff0c\u68c0\u6d4b\u5230\u591a\u4e2a\u6570\u636e\u96c6\u4e2d\u5b58\u5728\u91cd\u540d\u7684\u5b57\u6bb5\uff0c\u8bf7\u6307\u5b9a\u660e\u786e\u7684\u6570\u636e\u96c6\u5b57\u6bb5\u3002");
        }
        if (refDataSet == null) {
            throw new DynamicNodeException("\u67e5\u627e\u6307\u6807\u53c2\u6570[" + model.getName() + "]\u5bf9\u5e94\u7684\u6570\u636e\u96c6\u5b57\u6bb5[" + fieldName + "]\u65f6\uff0c\u65e0\u6cd5\u627e\u5230\u5bf9\u5e94\u7684\u5b57\u6bb5\u3002");
        }
        return new DSParamFieldNode(token, refDataSet, refField, model);
    }

    private IASTNode locateZBNode(ReportContext context, Token token, ParameterModel model, String dataSetName, String fieldName) throws DynamicNodeException {
        DSModel dsModel;
        try {
            dsModel = context.openDataSetModel(dataSetName);
        }
        catch (ReportContextException e) {
            throw new DynamicNodeException((Throwable)e);
        }
        if (dsModel == null) {
            throw new DynamicNodeException("\u67e5\u627e\u6307\u6807\u53c2\u6570[" + model.getName() + "]\u5bf9\u5e94\u7684\u6570\u636e\u96c6[" + dataSetName + "]\u4e0d\u5b58\u5728\u3002");
        }
        DSField dsField = dsModel.findField(fieldName);
        if (dsField == null) {
            throw new DynamicNodeException("\u67e5\u627e\u6307\u6807\u53c2\u6570[" + model.getName() + "]\u5bf9\u5e94\u7684\u6570\u636e\u96c6\u5b57\u6bb5[" + dataSetName + "." + fieldName + "]\u4e0d\u5b58\u5728\u3002");
        }
        return new DSParamFieldNode(token, dsModel, dsField, model);
    }

    protected IASTNode findZBNode(IContext context, Token token, String zbName, List<IASTNode> restrictItems, ParameterModel model) throws DynamicNodeException {
        DSParamFieldNode fieldNode = (DSParamFieldNode)this.findZBNode(context, token, zbName, model);
        fieldNode.getRestrictions().addAll(restrictItems);
        return fieldNode;
    }
}

