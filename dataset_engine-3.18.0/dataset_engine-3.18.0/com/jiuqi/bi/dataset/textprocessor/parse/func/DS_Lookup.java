/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TFieldNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;
import java.util.Locale;

public class DS_Lookup
extends TFunction {
    private static final long serialVersionUID = 6002606523085511392L;
    private static final String MODE_FIRST = "FIRST";
    private static final String MODE_LAST = "LAST";
    private static final String MODE_SUM = "SUM";
    private static final String MODE_MAX = "MAX";
    private static final String MODE_MIN = "MIN";
    private static final String MODE_AVG = "AVG";
    private static final String MODE_COUNT = "COUNT";

    public DS_Lookup() {
        this.parameters().add(new Parameter("lookupDS", 5100, "\u5f85\u67e5\u627e\u7684\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("value", 0, "\u5f85\u67e5\u627e\u7684\u503c"));
        this.parameters().add(new Parameter("lookupCol", 0, "\u67e5\u627e\u5217\uff0c\u53ef\u4ee5\u4e3a\u5217\u540d\u6216\u5b57\u6bb5\u5bf9\u8c61"));
        this.parameters().add(new Parameter("returnCol", 0, "\u8fd4\u56de\u503c\u5217\uff0c\u53ef\u4ee5\u4e3a\u5217\u540d\u6216\u5b57\u6bb5\u5bf9\u8c61\u3002\u7701\u7565\u65f6\u8fd4\u56de\u67e5\u627e\u5217\u503c", true));
        this.parameters().add(new Parameter("valMode", 6, "\u6d88\u7ef4\u6a21\u5f0f\uff0c\u53ef\u7701\u7565\u3002\u5f53\u67e5\u627e\u7ed3\u679c\u5b58\u5728\u591a\u4e2a\u65f6\uff0c\u53ef\u4ee5\u6307\u5b9a\u53d6\u503c\u65b9\u5f0f\u3002\u76ee\u524d\u652f\u6301\u7684\u7c7b\u578b\u5305\u62ec\uff1aFIRST\u3001LAST\u3001SUM\u3001MAX\u3001MIN\u3001AVG\u3001COUNT\uff0c\u4e0d\u6307\u5b9a\u65f6\u6570\u503c\u9ed8\u8ba4\u4f7f\u7528FIRST", true));
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return null;
    }

    @Override
    public boolean isDatasetNodeParam(int paramIndex) {
        return paramIndex == 0;
    }

    public String name() {
        return "DS_LOOKUP";
    }

    public String title() {
        return "\u6570\u636e\u96c6\u67e5\u627e\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        List<Integer> rows;
        int returnColIdx;
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        Locale locale = StringUtils.isEmpty((String)tfc.getI18nLang()) ? Locale.getDefault() : Locale.forLanguageTag(tfc.getI18nLang());
        BIDataSetImpl dataset = helper.evaluate(parameters.get(0), null, locale);
        Object searchValue = parameters.get(1).evaluate(context);
        String lookupColName = this.getColName(parameters.get(2), context);
        int lookupColIdx = dataset.getMetadata().indexOf(lookupColName);
        if (lookupColIdx == -1) {
            throw new SyntaxException("\u4f20\u5165\u7684\u5f85\u67e5\u627e\u5217\u540d\u79f0\u4e0d\u5b58\u5728");
        }
        String returnColName = lookupColName;
        if (parameters.size() >= 4) {
            returnColName = this.getColName(parameters.get(3), context);
        }
        if ((returnColIdx = dataset.getMetadata().indexOf(returnColName)) == -1) {
            throw new SyntaxException("\u4f20\u5165\u7684\u8fd4\u56de\u503c\u5217\u540d\u79f0\u4e0d\u5b58\u5728");
        }
        String valMode = MODE_FIRST;
        if (parameters.size() >= 5) {
            valMode = (String)parameters.get(4).evaluate(context);
            valMode = valMode.toUpperCase();
        }
        if ((rows = dataset.lookup(lookupColIdx, searchValue)).size() == 0) {
            return null;
        }
        if (valMode.equals(MODE_FIRST)) {
            int row = rows.get(0);
            return dataset.get(row).getValue(returnColIdx);
        }
        if (valMode.equals(MODE_LAST)) {
            int row = rows.get(rows.size() - 1);
            return dataset.get(row).getValue(returnColIdx);
        }
        if (valMode.equals(MODE_COUNT)) {
            return rows.size();
        }
        int[] rowIdx = new int[rows.size()];
        for (int i = 0; i < rows.size(); ++i) {
            rowIdx[i] = rows.get(i);
        }
        BIDataSetImpl subds = new BIDataSetImpl(dataset, rowIdx);
        try {
            if (valMode.equals(MODE_MAX)) {
                return subds.max(returnColIdx);
            }
            if (valMode.equals(MODE_MIN)) {
                return subds.min(returnColIdx);
            }
            if (valMode.equals(MODE_SUM)) {
                return subds.sum(returnColIdx);
            }
            if (valMode.equals(MODE_AVG)) {
                return subds.avg(returnColIdx);
            }
            throw new SyntaxException("\u4f20\u5165\u7684valMode\u683c\u5f0f\u9519\u8bef\uff0c\u53ea\u80fd\u4e3aFIRST\u3001LAST\u3001SUM\u3001MAX\u3001MIN\u3001AVG\u3001COUNT");
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    private String getColName(IASTNode colNode, IContext context) throws SyntaxException {
        if (colNode instanceof DataNode) {
            Object val = colNode.evaluate(context);
            if (val instanceof String) {
                return (String)val;
            }
            throw new SyntaxException("\u4f20\u9012\u53c2\u6570\u9519\u8bef\uff0c\u7b2c\u4e09\u4e2a\u53c2\u6570\u53ea\u80fd\u4e3a\u5217\u540d\u6216\u5b57\u6bb5\u5bf9\u8c61");
        }
        if (colNode instanceof TFieldNode) {
            return ((TFieldNode)colNode).getInfo().getName();
        }
        if (colNode instanceof DSFieldNode) {
            return ((DSFieldNode)colNode).getName();
        }
        throw new SyntaxException("\u4f20\u9012\u53c2\u6570\u9519\u8bef\uff0c\u7b2c\u4e09\u4e2a\u53c2\u6570\u53ea\u80fd\u4e3a\u5217\u540d\u6216\u5b57\u6bb5\u5bf9\u8c61");
    }
}

