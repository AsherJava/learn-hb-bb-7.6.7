/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.executor.AggrExecutor;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.AggrFunction;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.stat.StatConfig;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.dataset.stat.info.StatInfoHelper;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class Sum
extends AggrFunction {
    private static final long serialVersionUID = -2793554453186861730L;

    public Sum() {
        this.parameters().add(new Parameter("field", 0, "\u5ea6\u91cf\u5b57\u6bb5"));
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(DSFormulaContext context, BIDataSetImpl filterDs, StatInfo statInfo, List<IASTNode> paramNodes) throws SyntaxException {
        Object result;
        DSFieldNode fieldNode = (DSFieldNode)paramNodes.get(0);
        BIDataSetFieldInfo msFdInfo = fieldNode.getFieldInfo();
        BIDataSetImpl dataSet = filterDs;
        if (dataSet.getRecordCount() == 0) {
            result = null;
        } else {
            try {
                List<AggrMeasureItem> msItems = this.getAggrMeasureItem(context, paramNodes);
                AggrExecutor aggrExecutor = new AggrExecutor(dataSet);
                dataSet = aggrExecutor._aggregate(statInfo, new StatConfig(false, true));
                if (dataSet.getRecordCount() > 1) {
                    aggrExecutor = new AggrExecutor(dataSet);
                    dataSet = aggrExecutor.aggregateByItems(new ArrayList<Integer>(), msItems, true);
                }
                if (dataSet.getRecordCount() == 1) {
                    Column column = dataSet.getMetadata().find(msFdInfo.getName());
                    BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
                    if (info.isCalcField() && info.getCalcMode() == CalcMode.AGGR_THEN_CALC) {
                        dataSet.doCalcField(new int[]{column.getIndex()});
                    }
                    result = dataSet.get(0).getValue(column.getIndex());
                } else {
                    result = dataSet;
                }
            }
            catch (BIDataSetException e) {
                throw new SyntaxException("\u6267\u884c\u6570\u636e\u96c6\u805a\u5408\u65f6\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
            }
        }
        return result;
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node = parameters.get(0);
        if (!(node instanceof DSFieldNode)) {
            throw new SyntaxException("\u51fd\u6570\u53c2\u6570\u9519\u8bef\uff0c\u8282\u70b9\u3010" + node.interpret(context, Language.FORMULA, null) + "\u3011\u4e0d\u662f\u4e00\u4e2a\u6570\u636e\u96c6\u5b57\u6bb5\u8282\u70b9");
        }
        DSFieldNode dsNode = (DSFieldNode)node;
        BIDataSetFieldInfo info = dsNode.getFieldInfo();
        if (info.getFieldType() != FieldType.MEASURE) {
            throw new SyntaxException("\u5b57\u6bb5" + info.getName() + "\u4e0d\u662f\u4e00\u4e2a\u5ea6\u91cf\u5b57\u6bb5");
        }
        return super.validate(context, parameters);
    }

    public String name() {
        return "DS_SUM";
    }

    public String title() {
        return "\u8ba1\u7b97\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u8fc7\u6ee4\u540e\u7684\u6570\u636e\u96c6\u4e2d\u6307\u5b9a\u5ea6\u91cf\u4e4b\u548c";
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 3;
    }

    @Override
    public List<AggrMeasureItem> getAggrMeasureItem(DSFormulaContext context, List<IASTNode> paramNodes) throws SyntaxException {
        IASTNode msNode = paramNodes.get(0);
        DSFieldNode fieldNode = (DSFieldNode)msNode;
        BIDataSetFieldInfo msFdInfo = fieldNode.getFieldInfo();
        Metadata<BIDataSetFieldInfo> metadata = context.getDataSet().getMetadata();
        Column mscolumn = metadata.find(msFdInfo.getName());
        if (mscolumn == null) {
            throw new SyntaxException("\u5b57\u6bb5" + msFdInfo.getName() + "\u4e0d\u5b58\u5728");
        }
        ArrayList<AggrMeasureItem> msItems = new ArrayList<AggrMeasureItem>();
        msItems.add(new AggrMeasureItem(msFdInfo.getName(), null, msFdInfo.getTitle(), AggregationType.SUM));
        if (msFdInfo.isCalcField()) {
            List columns = metadata.getColumns();
            for (Column column : columns) {
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
                if (info.getName().equals(msFdInfo.getName()) || info.getFieldType() != FieldType.MEASURE) continue;
                msItems.add(new AggrMeasureItem(info.getName(), null, null, info.getAggregation()));
            }
        }
        return msItems;
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        DSFormulaContext dsCxt = (DSFormulaContext)context;
        BIDataSetImpl dataset = (BIDataSetImpl)filterDs;
        StatInfoHelper helper = new StatInfoHelper(dataset, new StatConfig(false, false));
        ArrayList<AggrMeasureItem> reserveMsList = new ArrayList<AggrMeasureItem>();
        reserveMsList.addAll(this.getAggrMeasureItem(dsCxt, paramNodes));
        StatInfo statInfo = helper.createStatInfo(new ArrayList<Integer>(), reserveMsList);
        return this.evalute(dsCxt, dataset, statInfo, paramNodes);
    }
}

