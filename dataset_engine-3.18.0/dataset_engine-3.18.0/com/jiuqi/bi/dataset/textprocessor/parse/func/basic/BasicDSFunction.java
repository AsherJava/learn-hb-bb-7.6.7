/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.RestrictTagNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class BasicDSFunction
extends TFunction {
    private static final long serialVersionUID = 7774650306502865399L;
    protected DSFunction dsFunc;

    public BasicDSFunction() {
    }

    public BasicDSFunction(DSFunction dsFunc) {
        this.parameters().add(new Parameter("ds", 5100, "\u6570\u636e\u96c6"));
        this.dsFunc = dsFunc;
        for (IParameter param : dsFunc.parameters()) {
            this.parameters().add(param);
        }
    }

    public DSFunction getOrgFunction() {
        return this.dsFunc;
    }

    protected abstract List<IASTNode> getFilterParamList(IContext var1, List<IASTNode> var2);

    public String name() {
        return this.dsFunc.name();
    }

    public String title() {
        return this.dsFunc.title();
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return this.dsFunc.getResultType(null, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        BIDataSetImpl dataset = this.getDataset(context, parameters = helper.convertToDSFormulaContextNode(parameters.get(0), parameters));
        if (dataset.getRecordCount() > 1 && this.isAutoAggr()) {
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
        List<IASTNode> dsParams = this.getDSFunctionParameterList(context, parameters);
        List<IASTNode> filters = this.getFilterParamList(context, parameters);
        dsParams.removeAll(filters);
        return this.dsFunc.evalute(dsCxt, dsParams, dataset);
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        String dsName = this.getDSName(parameters);
        DSHelper helper = new DSHelper(tfc);
        for (int i = 0; i < parameters.size(); ++i) {
            IASTNode param = parameters.get(i);
            param = helper.adjust(dsName, param);
            parameters.set(i, param);
        }
        return super.validate(context, parameters);
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return this.dsFunc.getDataFormator(null);
    }

    @Override
    public boolean isDatasetNodeParam(int paramIndex) {
        return paramIndex == 0;
    }

    protected List<IASTNode> getDSFunctionParameterList(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return new ArrayList<IASTNode>(parameters.subList(1, parameters.size()));
    }

    private BIDataSetImpl getDataset(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        IASTNode p0 = parameters.get(0);
        List<IASTNode> filters = this.getFilterParamList(context, parameters);
        Locale locale = StringUtils.isEmpty((String)tfc.getI18nLang()) ? Locale.getDefault() : Locale.forLanguageTag(tfc.getI18nLang());
        return helper.evaluate(p0, filters, locale);
    }

    protected String getDSName(List<IASTNode> parameters) {
        for (int i = 0; i < parameters.size(); ++i) {
            if (!this.isDatasetNodeParam(i)) continue;
            IASTNode param = parameters.get(i);
            for (IASTNode nd : param) {
                if (!(nd instanceof DSNode)) continue;
                return ((DSNode)nd).getDSName();
            }
        }
        return null;
    }

    protected Set<String> statAppearFieldName(TextFormulaContext tfc, List<IASTNode> parameters) {
        IASTNode p0;
        HashSet<String> appearNodes = new HashSet<String>();
        HashSet<String> mbAllNodes = new HashSet<String>();
        String dsName = this.getDSName(parameters);
        for (IASTNode filterNd : parameters) {
            this.findoutFieldNode(appearNodes, mbAllNodes, filterNd, dsName);
        }
        if (tfc._getAST() != null) {
            this.findoutFieldNode(appearNodes, mbAllNodes, tfc._getAST(), dsName);
        }
        if ((p0 = parameters.get(0)) instanceof DSNode) {
            DSNode dsNode = (DSNode)p0;
            List<FilterItem> filters = tfc.getFormulaFilter(dsNode.getDSName());
            for (FilterItem fi : filters) {
                appearNodes.add(fi.getFieldName().toUpperCase());
            }
        }
        if (this.isTreatALLRstAsAggr()) {
            appearNodes.removeAll(mbAllNodes);
        }
        return appearNodes;
    }

    protected boolean isTreatALLRstAsAggr() {
        return true;
    }

    protected boolean isAutoAggr() {
        return true;
    }

    private void findoutFieldNode(Set<String> appearNodes, Set<String> mbAllSets, IASTNode root, String dsName) {
        Iterator itor = root.iterator();
        HashSet<IASTNode> tmp = new HashSet<IASTNode>();
        while (itor.hasNext()) {
            IASTNode node = (IASTNode)itor.next();
            if (tmp.contains(node)) continue;
            if (node instanceof DSFieldNode) {
                BIDataSetFieldInfo info = ((DSFieldNode)node).getFieldInfo();
                appearNodes.add(info.getName().toUpperCase());
                continue;
            }
            if (!(node instanceof Equal)) continue;
            IASTNode left = node.getChild(0);
            IASTNode right = node.getChild(1);
            if (!(left instanceof DSFieldNode)) continue;
            DSFieldNode fieldNode = (DSFieldNode)left;
            BIDataSetFieldInfo info = fieldNode.getFieldInfo();
            if (right instanceof RestrictTagNode) {
                String tag = ((RestrictTagNode)right).getTag();
                if (RestrictionTag.isMB(tag) || RestrictionTag.isALL(tag)) {
                    mbAllSets.add(info.getName().toUpperCase());
                }
                appearNodes.add(info.getName().toUpperCase());
            } else {
                appearNodes.add(info.getName().toUpperCase());
            }
            tmp.add(left);
            tmp.add(right);
        }
    }
}

