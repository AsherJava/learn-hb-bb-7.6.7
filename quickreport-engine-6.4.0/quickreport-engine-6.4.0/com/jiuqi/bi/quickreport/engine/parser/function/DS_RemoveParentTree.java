/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.quickreport.engine.parser.QParserHelper;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class DS_RemoveParentTree
extends DataSetFunction {
    private static final long serialVersionUID = 1L;

    public DS_RemoveParentTree() {
        this.parameters().add(new Parameter("dataset", 5050, "\u6570\u636e\u96c6"));
    }

    public String name() {
        return "DS_RemoveParentTree";
    }

    public String[] aliases() {
        return new String[]{"DS_RPT"};
    }

    public String title() {
        return "\u79fb\u9664\u6570\u636e\u96c6\u4e2d\u7684\u7236\u5b50\u5c42\u7ea7\uff08\u4f1a\u5220\u9664\u7236\u4ee3\u7801\u5b57\u6bb5\uff09\uff0c\u53ef\u7b80\u5199\u4e3aDS_RPT\u3002";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 5050;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode param = parameters.get(0);
        if (param instanceof DataSetNode) {
            String dsName = ((DataSetNode)param).getDataSetModel().getName();
            try {
                return ((ReportContext)context).openDataSetWithoutParent(dsName);
            }
            catch (ReportContextException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        BIDataSet rawDS = (BIDataSet)param.evaluate(context);
        DSModel model = ((IDataSetNode)param).getDataSetModel();
        try {
            return QParserHelper.removeParentFields(rawDS, model);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
    }
}

