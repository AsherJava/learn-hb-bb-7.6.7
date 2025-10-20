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
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Count
extends DSFunction {
    private static final long serialVersionUID = -3086847146188406710L;

    public Count() {
        this.parameters().add(new Parameter("expr", 0, "\u5e38\u91cf\u6216\u5b57\u6bb5\u540d\u79f0"));
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        BIDataSetImpl dataset = (BIDataSetImpl)filterDs;
        IASTNode p0 = paramNodes.get(0);
        if (p0 instanceof DataNode) {
            return dataset.getRecordCount();
        }
        if (p0 instanceof DSFieldNode) {
            DSFieldNode fdNode = (DSFieldNode)p0;
            BIDataSetFieldInfo info = fdNode.getFieldInfo();
            if (!info.getFieldType().isDimField()) {
                return dataset.getRecordCount();
            }
            try {
                ArrayList<String> names = new ArrayList<String>();
                names.add(info.getName());
                return dataset.distinctCount(names);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException("\u6267\u884ccount\u51fa\u9519\uff0c" + e.getMessage(), (Throwable)e);
            }
        }
        throw new SyntaxException("\u8ba1\u6570\u7684\u53c2\u6570\u53ea\u80fd\u4e3a\u4e00\u4e2a\u5e38\u91cf\u6216\u8005\u4e00\u4e2a\u5b57\u6bb5\u540d\u79f0");
    }

    public String name() {
        return "DS_COUNT";
    }

    public String title() {
        return "\u7edf\u8ba1\u6570\u636e\u96c6\u8bb0\u5f55\u6570\u3002\u5982\u679c\u662f\u7ef4\u5ea6\u5b57\u6bb5\uff0c\u8fd4\u56de\u4e0d\u91cd\u590d\u7684\u8bb0\u5f55\u4e2a\u6570\uff08\u7a7a\u503c\u4e0d\u53c2\u4e0e\u8ba1\u6570\uff09\uff1b\u5982\u679c\u662f\u5ea6\u91cf\u5b57\u6bb5\u6216\u8005\u662f\u4e00\u4e2a\u5e38\u91cf\u8868\u8fbe\u5f0f\uff0c\u5219\u8fd4\u56de\u8fc7\u6ee4\u540e\u6570\u636e\u96c6\u7684\u603b\u8bb0\u5f55\u6570";
    }

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

