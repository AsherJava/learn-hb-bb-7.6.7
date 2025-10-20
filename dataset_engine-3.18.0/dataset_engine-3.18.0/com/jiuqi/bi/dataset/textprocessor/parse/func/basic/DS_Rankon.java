/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.keyword.KeywordNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.BasicDSFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TFieldNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.keyword.KeywordNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DS_Rankon
extends BasicDSFunction {
    private static final long serialVersionUID = 1254425352052814357L;

    public DS_Rankon() {
        this.parameters().add(new Parameter("ds", 5100, "\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("expr", 0, "\u5ea6\u91cf\u5b57\u6bb5\u6216\u8ba1\u7b97\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("sortType", 0, "\u6392\u5e8f\u7c7b\u578b\uff1aASC\u6216DESC"));
        this.parameters().add(new Parameter("restrict", 1, "\u9650\u5b9a\u8868\u8fbe\u5f0f", true));
    }

    @Override
    protected List<IASTNode> getFilterParamList(IContext context, List<IASTNode> parameters) {
        return new ArrayList<IASTNode>(parameters.subList(3, parameters.size()));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        IASTNode p0 = parameters.get(0);
        List<IASTNode> filters = this.getFilterParamList(context, parameters);
        ArrayList<String> calcFds = new ArrayList<String>();
        String rankExpr = this.getRankExpr(parameters.get(1), context);
        calcFds.add(rankExpr);
        BIDataSetImpl dataset = (BIDataSetImpl)p0.evaluate((IContext)tfc);
        DSFormulaContext dsFx = new DSFormulaContext(dataset);
        dsFx.setLanguage(tfc.getI18nLang());
        try {
            dataset = (BIDataSetImpl)dataset.doFilter(filters, dsFx);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        if (dataset.getRecordCount() == 0) {
            return -1;
        }
        int sortType = this.getSortType(parameters.get(2));
        dataset = this.sortDatasetByNode(rankExpr, dataset, context, sortType);
        if (p0 instanceof DSNode) {
            String dsName = ((DSNode)p0).getDSName();
            List<FilterItem> df = tfc.getFormulaFilter(dsName);
            if (df != null && df.size() > 0) {
                List<Integer> rs = dataset.getDSIndex()._filter(df);
                return rs.size() > 0 ? rs.get(0) + 1 : -1;
            }
            return 1;
        }
        throw new SyntaxException("DS_RANKON\u51fd\u6570\u4e2d\u4e0d\u5141\u8bb8\u5d4c\u5957\u5176\u4ed6\u6570\u636e\u96c6\u51fd\u6570");
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

    private BIDataSetImpl sortDatasetByNode(String rankExpr, BIDataSetImpl dataset, IContext context, int sortType) throws SyntaxException {
        int colIdx = dataset.getMetadata().indexOf(rankExpr);
        if (colIdx == -1) {
            try {
                HashMap<String, String> calcFieldMap = new HashMap<String, String>();
                calcFieldMap.put(rankExpr, rankExpr);
                dataset = (BIDataSetImpl)dataset.addCalcFields(calcFieldMap);
                colIdx = dataset.getMetadata().indexOf(rankExpr);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        dataset._markFieldHasCalc(colIdx);
        return (BIDataSetImpl)dataset.sort(colIdx, sortType);
    }

    private String getRankExpr(IASTNode node, IContext context) throws SyntaxException {
        if (node instanceof TFieldNode) {
            return ((TFieldNode)node).getInfo().getName();
        }
        if (node instanceof DSFieldNode) {
            return ((DSFieldNode)node).getName();
        }
        return node.interpret(context, Language.FORMULA, null);
    }

    @Override
    protected boolean isTreatALLRstAsAggr() {
        return false;
    }

    @Override
    public String name() {
        return "DS_RANKON";
    }

    @Override
    public String title() {
        return "\u83b7\u53d6\u9650\u5b9a\u8868\u8fbe\u5f0f\u9650\u5b9a\u540e\u7684\u6570\u636e\u96c6\u5728\u539f\u59cb\u6570\u636e\u96c6\u4e2d\uff0c\u6307\u5b9a\u5ea6\u91cf\u7684\u6392\u540d";
    }

    @Override
    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 3;
    }

    @Override
    public IDataFormator getDataFormator(IContext context) {
        return new IDataFormator(){

            public Format getFormator(IContext context) {
                DecimalFormat format = new DecimalFormat("#0");
                ((NumberFormat)format).setGroupingUsed(true);
                return format;
            }
        };
    }
}

