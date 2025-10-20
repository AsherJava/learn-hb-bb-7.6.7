/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.executor.AggrExecutor;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.restrict.CondKey;
import com.jiuqi.bi.dataset.restrict.FilterOptimizer;
import com.jiuqi.bi.dataset.stat.StatConfig;
import com.jiuqi.bi.dataset.stat.StatProcessor;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSRestrictNode
extends DynamicNode {
    private static final long serialVersionUID = 1L;
    private final String dsName;
    private final String fdName;
    private DSModel dsModel;
    private BIDataSetFieldInfo info;
    private final List<IASTNode> restricts;
    private Map<CondKey, Object> cache = new HashMap<CondKey, Object>();
    private List<AggrMeasureItem> aggrItems;
    private StatInfo statInfo;
    private FilterOptimizer optimizer;

    public DSRestrictNode(Token token, String dsName, String fdName, List<IASTNode> restrictItems) {
        super(token);
        this.dsName = dsName;
        this.fdName = fdName;
        this.restricts = restrictItems == null ? new ArrayList<IASTNode>() : new ArrayList<IASTNode>(restrictItems);
    }

    public List<IASTNode> getRestrictItems() {
        return this.restricts;
    }

    public void setOptimizer(FilterOptimizer optimizer) {
        this.optimizer = optimizer;
    }

    public void setDsModel(DSModel dsModel) {
        this.dsModel = dsModel;
    }

    public void setInfo(BIDataSetFieldInfo info) {
        this.info = info;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        DSFormulaContext dsCxt = (DSFormulaContext)context;
        BIDataSetImpl dataset = dsCxt.getDataSet();
        BIDataRow curRow = dsCxt.getCurRow();
        if (curRow != null && this.restricts.size() == 0) {
            Column column = dataset.getMetadata().find(this.fdName);
            return curRow.getValue(column.getIndex());
        }
        CondKey key = null;
        try {
            key = this.optimizer.calcKey((IASTNode)this, dsCxt);
            Object result = this.cache.get(key);
            if (result != null) {
                return result;
            }
            dataset = this.optimizer.evalFilter((IASTNode)this, dsCxt);
            if (dataset.getRecordCount() == 0) {
                return null;
            }
        }
        catch (BIDataSetException e) {
            throw new SyntaxException("\u6267\u884c\u6570\u636e\u96c6\u8fc7\u6ee4\u51fa\u9519\uff0c" + e.getMessage(), (Throwable)e);
        }
        try {
            AggrExecutor aggrExecutor;
            Column column;
            BIDataSetFieldInfo info;
            ArrayList<Integer> hasRestDims = new ArrayList<Integer>();
            if (this.statInfo == null) {
                this.initAggrMetadata(dataset, hasRestDims);
            }
            if ((info = (BIDataSetFieldInfo)(column = (dataset = (aggrExecutor = new AggrExecutor(dataset))._aggregate(this.statInfo, new StatConfig(false, false))).getMetadata().find(this.fdName)).getInfo()).isCalcField() && info.getCalcMode() == CalcMode.AGGR_THEN_CALC) {
                ArrayList<Integer> colIdxList = new ArrayList<Integer>(1);
                colIdxList.add(column.getIndex());
                dataset.compute(colIdxList);
            }
            Object result = dataset.get(0).getValue(column.getIndex());
            this.cache.put(key, result);
            return result;
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public int getType(IContext context) throws SyntaxException {
        DSFormulaContext dsCxt = (DSFormulaContext)context;
        BIDataSetImpl dataset = dsCxt.getDataSet();
        Column column = dataset.getMetadata().find(this.fdName);
        if (column != null) {
            int tp = ((BIDataSetFieldInfo)column.getInfo()).getValType();
            return DataType.translateToSyntaxType(DataType.valueOf(tp));
        }
        throw new SyntaxException("\u6307\u5b9a\u7684\u5b57\u6bb5\u540d\u79f0" + this.fdName + "\u4e0d\u5b58\u5728");
    }

    public int validate(IContext context) throws SyntaxException {
        return this.getType(context);
    }

    public void toString(StringBuilder buffer) {
        buffer.append("[");
        if (this.dsName != null) {
            buffer.append(this.dsName).append(".");
        }
        buffer.append(this.fdName);
        if (this.restricts.size() > 0) {
            for (int i = 0; i < this.restricts.size(); ++i) {
                buffer.append(", ").append(this.restricts.get(i).toString());
            }
        }
        buffer.append("]");
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("[");
        if (this.dsName != null) {
            buffer.append(this.dsName).append(".");
        }
        buffer.append(this.fdName);
        if (this.restricts.size() > 0) {
            for (int i = 0; i < this.restricts.size(); ++i) {
                buffer.append(", ");
                this.restricts.get(i).interpret(context, buffer, Language.FORMULA, info);
            }
        }
        buffer.append("]");
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("[");
        if (this.dsModel != null) {
            buffer.append(this.dsModel.getTitle()).append(".");
        } else if (this.dsModel != null) {
            buffer.append(this.dsName).append(".");
        }
        if (info != null) {
            buffer.append(StringUtils.isEmpty((String)this.info.getTitle()) ? this.info.getName() : this.info.getTitle());
        } else {
            buffer.append(this.fdName);
        }
        if (this.restricts.size() > 0) {
            for (int i = 0; i < this.restricts.size(); ++i) {
                buffer.append(", ");
                this.restricts.get(i).interpret(context, buffer, Language.EXPLAIN, info);
            }
        }
        buffer.append("]");
    }

    private void initAggrMetadata(BIDataSetImpl dataset, List<Integer> hasRestDims) throws BIDataSetException {
        this.aggrItems = new ArrayList<AggrMeasureItem>(1);
        AggregationType aggrType = ((BIDataSetFieldInfo)dataset.getMetadata().find(this.fdName).getInfo()).getAggregation();
        this.aggrItems.add(new AggrMeasureItem(this.fdName, this.fdName, null, aggrType));
        StatProcessor statProcessor = new StatProcessor(dataset, new StatConfig(false, false));
        this.statInfo = statProcessor.createStatInfo(hasRestDims, this.aggrItems);
    }
}

