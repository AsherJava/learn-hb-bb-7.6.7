/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.MOM;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.BasicDSFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DS_MOM
extends BasicDSFunction {
    private static final long serialVersionUID = 7594749205753244195L;

    public DS_MOM() {
        super(new MOM());
    }

    @Override
    protected List<IASTNode> getFilterParamList(IContext context, List<IASTNode> parameters) {
        return new ArrayList<IASTNode>(parameters.subList(2, parameters.size()));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        DSNode dsNode = (DSNode)parameters.get(0);
        BIDataSetImpl dataset = (BIDataSetImpl)dsNode.evaluate(context);
        if (dataset.getRecordCount() == 0) {
            return null;
        }
        if (dataset.getRecordCount() > 1) {
            Set<String> appearDims = this.statAppearFieldName(tfc, parameters);
            try {
                dataset = helper.doAggregate(dataset, appearDims);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        DSFormulaContext dsCxt = new DSFormulaContext(dataset);
        dsCxt.setLanguage(tfc.getI18nLang());
        ArrayList<IASTNode> params = new ArrayList<IASTNode>();
        List<IASTNode> pList = parameters.subList(1, parameters.size());
        for (IASTNode nd : pList) {
            params.add(helper.adjust(dsNode.getDSName(), nd));
        }
        List<FilterItem> globalFilters = helper.analysisFilter(dsNode.getDSModel(), this.getFilterParamList(context, parameters));
        BIDataSet filterDs = dataset;
        if (globalFilters.size() > 0) {
            List<IASTNode> nodes = helper.convertFilterItemToFilterNode(dsNode.getDSModel(), globalFilters);
            params.addAll(nodes);
            try {
                filterDs = dataset.doFilter(this.getFilterParamList(context, parameters), dsCxt);
                filterDs = filterDs.filter(globalFilters);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        try {
            if (parameters.size() > 2) {
                filterDs = dataset.doFilter(this.getFilterParamList(context, parameters), dsCxt);
            }
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        return this.dsFunc.evalute(dsCxt, params, filterDs);
    }

    @Override
    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode p0 = parameters.get(0);
        if (!(p0 instanceof DSNode)) {
            throw new SyntaxException("\u51fd\u6570DS_MOM\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u4e00\u4e2a\u6570\u636e\u96c6\u6807\u8bc6");
        }
        return super.validate(context, parameters);
    }
}

