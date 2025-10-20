/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.keyword.KeywordNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.keyword.KeywordNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RankAt
extends DSFunction {
    private static final long serialVersionUID = 7084284329215055298L;

    public RankAt() {
        this.parameters().add(new Parameter("n", 3, "\u6392\u540d\u503c"));
        this.parameters().add(new Parameter("expr", 0, "\u5ea6\u91cf\u5b57\u6bb5\u6216\u8ba1\u7b97\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("sortType", 0, "\u6392\u5e8f\u7c7b\u578b\uff1aASC\u6216DESC"));
        this.parameters().add(new Parameter("restrict", 1, "\u9650\u5b9a\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        BIDataSetImpl dataset = (BIDataSetImpl)filterDs;
        String expr = paramNodes.get(1).interpret(context, Language.FORMULA, null);
        int colIdx = dataset.getMetadata().indexOf(expr);
        if (colIdx == -1) {
            try {
                HashMap<String, String> calcFieldMap = new HashMap<String, String>();
                calcFieldMap.put(expr, expr);
                dataset = (BIDataSetImpl)dataset.addCalcFields(calcFieldMap);
                colIdx = dataset.getMetadata().indexOf(expr);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
        }
        int sortType = this.getSortType(paramNodes.get(2));
        dataset = (BIDataSetImpl)dataset.sort(colIdx, sortType);
        Number nObj = (Number)paramNodes.get(0).evaluate(context);
        if (nObj == null) {
            throw new SyntaxException("\u4f20\u5165\u7684\u53c2\u6570n\u4e3a\u7a7a\u503c");
        }
        int n = nObj.intValue();
        if (n < 0) {
            throw new SyntaxException("\u4f20\u5165\u7684\u6392\u540d\u503c\u5fc5\u987b\u5927\u4e8e0");
        }
        int[] rowIdxes = this.getRows(dataset, colIdx, n);
        return new BIDataSetImpl(dataset, rowIdxes);
    }

    public String name() {
        return "DS_RANKAT";
    }

    public String title() {
        return "\u5bf9\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u8fc7\u6ee4\u540e\u7684\u6570\u636e\u96c6\u6309\u7167\u6307\u5b9a\u7684\u5b57\u6bb5\u3001\u6307\u5b9a\u7684\u6392\u5e8f\u65b9\u5f0f\u8fdb\u884c\u6392\u5e8f\uff0c\u83b7\u53d6\u4f4d\u4e8e\u6392\u540dn\u5904\u7684\u8bb0\u5f55";
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 5100;
    }

    private int getSortType(IASTNode knode) throws SyntaxException {
        if (knode instanceof KeywordNode) {
            String name = ((KeywordNode)knode).getKeyword();
            if (name.equalsIgnoreCase("ASC")) {
                return 1;
            }
            if (name.equalsIgnoreCase("DESC")) {
                return -1;
            }
            throw new SyntaxException("\u5b57\u6bb5\u6392\u5e8f\u7c7b\u578b\u53ea\u80fd\u4e3aASC\u6216\u8005DESC");
        }
        throw new SyntaxException("\u5b57\u6bb5\u6392\u5e8f\u7c7b\u578b\u53ea\u80fd\u4e3aASC\u6216\u8005DESC");
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

