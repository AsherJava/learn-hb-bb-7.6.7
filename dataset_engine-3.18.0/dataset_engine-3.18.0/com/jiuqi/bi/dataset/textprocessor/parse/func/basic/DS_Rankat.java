/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.keyword.KeywordNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.BasicDSFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TFieldNode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.keyword.KeywordNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DS_Rankat
extends BasicDSFunction {
    private static final long serialVersionUID = -699612586972491705L;

    public DS_Rankat() {
        this.parameters().add(new Parameter("ds", 5100, "\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("n", 3, "\u6392\u540d\u503c"));
        this.parameters().add(new Parameter("expr", 0, "\u5ea6\u91cf\u5b57\u6bb5\u6216\u8ba1\u7b97\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("sortType", 0, "\u6392\u5e8f\u7c7b\u578b\uff1aASC\u6216DESC"));
        this.parameters().add(new Parameter("restrict", 1, "\u9650\u5b9a\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public boolean isDatasetNodeParam(int paramIndex) {
        return paramIndex == 0;
    }

    @Override
    public String name() {
        return "DS_RANKAT";
    }

    @Override
    public String title() {
        return "\u6839\u636e\u9650\u5b9a\u6761\u4ef6\u5bf9\u6570\u636e\u96c6\u8fdb\u884c\u8fc7\u6ee4\uff0c\u7136\u540e\u6309expr\u5bf9\u8fc7\u6ee4\u540e\u7684\u6570\u636e\u96c6\u8fdb\u884c\u6392\u5e8f\uff0c\u8fd4\u56de\u6307\u5b9a\u6392\u540d\u503c\u5904\u7684\u884c\u96c6\u5408";
    }

    @Override
    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 5100;
    }

    @Override
    protected List<IASTNode> getFilterParamList(IContext context, List<IASTNode> parameters) {
        return new ArrayList<IASTNode>(parameters.subList(4, parameters.size()));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Locale locale;
        ArrayList<IASTNode> filters;
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        IASTNode p0 = parameters.get(0);
        BIDataSetImpl dataset = helper.evaluate(p0, filters = new ArrayList<IASTNode>(parameters.subList(4, parameters.size())), locale = StringUtils.isEmpty((String)tfc.getI18nLang()) ? Locale.getDefault() : Locale.forLanguageTag(tfc.getI18nLang()));
        if (dataset.getRecordCount() > 1) {
            Set<String> appearDims = this.statAppearFieldName(tfc, parameters);
            try {
                dataset = helper.doAggregate(dataset, appearDims);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        int sortType = this.getSortType(parameters.get(3));
        BIDataSet[] newdataset = new BIDataSet[1];
        int colIdx = this.getSortExprColIdx(parameters.get(2), dataset, context, newdataset);
        if (newdataset[0] != null) {
            dataset = (BIDataSetImpl)newdataset[0];
        }
        dataset = (BIDataSetImpl)dataset.sort(colIdx, sortType);
        Number nObj = (Number)parameters.get(1).evaluate(context);
        if (nObj == null) {
            throw new SyntaxException("\u4f20\u5165\u7684\u53c2\u6570n\u4e3a\u7a7a\u503c");
        }
        int n = nObj.intValue();
        if (n <= 0) {
            throw new SyntaxException("\u4f20\u5165\u7684\u6392\u540d\u503c\u5fc5\u987b\u5927\u4e8e0");
        }
        int[] rowIdxes = this.getRows(dataset, colIdx, n);
        return new BIDataSetImpl(dataset, rowIdxes);
    }

    @Override
    protected boolean isTreatALLRstAsAggr() {
        return false;
    }

    @Override
    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getSortType(IASTNode node) throws SyntaxException {
        int sortType = -1;
        if (!(node instanceof KeywordNode)) throw new SyntaxException("\u5b57\u6bb5\u6392\u5e8f\u7c7b\u578b\u53ea\u80fd\u4e3aASC\u6216\u8005DESC");
        String name = ((KeywordNode)node).getKeyword();
        if (name.equalsIgnoreCase("ASC")) {
            return 1;
        }
        if (!name.equalsIgnoreCase("DESC")) throw new SyntaxException("\u5b57\u6bb5\u6392\u5e8f\u7c7b\u578b\u53ea\u80fd\u4e3aASC\u6216\u8005DESC");
        return -1;
    }

    private int getSortExprColIdx(IASTNode node, BIDataSet dataset, IContext context, BIDataSet[] newdataset) throws SyntaxException {
        int colIdx;
        if (node instanceof TFieldNode) {
            String expr = ((TFieldNode)node).getInfo().getName();
            colIdx = dataset.getMetadata().indexOf(expr);
        } else if (node instanceof DSFieldNode) {
            String expr = ((DSFieldNode)node).getName();
            colIdx = dataset.getMetadata().indexOf(expr);
        } else {
            String expr = node.interpret(context, Language.FORMULA, null);
            try {
                HashMap<String, String> calcFieldMap = new HashMap<String, String>();
                calcFieldMap.put("_dscalc1", expr);
                newdataset[0] = dataset = (BIDataSetImpl)dataset.addCalcFields(calcFieldMap);
                colIdx = dataset.getMetadata().indexOf("_dscalc1");
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        return colIdx;
    }

    private int[] getRows(BIDataSetImpl dataset, int colIdx, int n) {
        int count = 0;
        int start = 0;
        int len = 0;
        Iterator<BIDataRow> itor = dataset.iterator();
        Object lastObj = null;
        while (itor.hasNext()) {
            BIDataRow dataRow = itor.next();
            Object curObj = dataRow.getValue(colIdx);
            int compareTo = DataType.compareObject(lastObj, (Object)curObj);
            if (compareTo != 0 && count != n) {
                ++count;
                lastObj = curObj;
            }
            if (count != n) {
                ++start;
                continue;
            }
            if (len == 0) {
                len = 1;
                continue;
            }
            if (compareTo != 0) break;
            ++len;
        }
        int[] rowIdxes = new int[len];
        for (int i = 0; i < len; ++i) {
            rowIdxes[i] = start + i;
        }
        return rowIdxes;
    }
}

