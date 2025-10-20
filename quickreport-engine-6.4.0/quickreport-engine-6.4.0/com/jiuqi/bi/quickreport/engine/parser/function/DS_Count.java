/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class DS_Count
extends DataSetFunction {
    private static final long serialVersionUID = 1L;

    public DS_Count() {
        this.parameters().add(new Parameter("dataset", 5050, "\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("field", 0, "\u8ba1\u6570\u7684\u6570\u636e\u96c6\u5b57\u6bb5\u6216\u5e38\u91cf"));
        this.parameters().add(new Parameter("filter", 0, "\u8fc7\u6ee4\u6761\u4ef6", true));
    }

    public String name() {
        return "DS_Count";
    }

    public String title() {
        return "\u6570\u636e\u96c6\u8ba1\u6570\u7edf\u8ba1";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object result;
        ArrayList<IASTNode> restrictions = new ArrayList<IASTNode>();
        for (int i = 2; i < parameters.size(); ++i) {
            restrictions.add(parameters.get(i));
        }
        BIDataSet dataSet = this.openDataSet(context, parameters.get(0), parameters.get(1), restrictions);
        IASTNode fieldNode = parameters.get(1);
        if (fieldNode instanceof DataNode) {
            result = dataSet.getRecordCount();
            this.evalTracer((ReportContext)context, parameters.get(0), null, restrictions, result);
        } else {
            int colIndex = DS_Count.findColumn(context, dataSet, fieldNode);
            Column column = dataSet.getMetadata().getColumn(colIndex);
            if (((BIDataSetFieldInfo)column.getInfo()).isDimention()) {
                int keyIndex = -1;
                if (!StringUtils.isEmpty((String)((BIDataSetFieldInfo)column.getInfo()).getKeyField()) && !column.getName().equalsIgnoreCase(((BIDataSetFieldInfo)column.getInfo()).getKeyField())) {
                    keyIndex = dataSet.getMetadata().indexOf(((BIDataSetFieldInfo)column.getInfo()).getKeyField());
                }
                result = this.distinctCount(dataSet, keyIndex < 0 ? colIndex : keyIndex);
            } else {
                result = this.count(dataSet, colIndex);
            }
            this.evalTracer((ReportContext)context, parameters.get(0), fieldNode, restrictions, result);
        }
        return result;
    }

    private Object distinctCount(BIDataSet dataSet, int colIndex) {
        HashSet<Object> values = new HashSet<Object>();
        for (BIDataRow row : dataSet) {
            Object value = row.getValue(colIndex);
            if (value == null) continue;
            values.add(value);
        }
        return values.size();
    }

    private Object count(BIDataSet dataSet, int colIndex) {
        int size = 0;
        for (BIDataRow row : dataSet) {
            Object value = row.getValue(colIndex);
            if (value == null) continue;
            ++size;
        }
        return size;
    }

    @Override
    protected boolean needAggregation(DSField field) {
        return false;
    }
}

