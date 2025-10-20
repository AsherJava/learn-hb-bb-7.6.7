/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSFilter;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.idx.DSIndex;
import com.jiuqi.bi.dataset.idx.IdxFilterInfo;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.restrict.AggrCondition;
import com.jiuqi.bi.dataset.restrict.ExpressionCondition;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.OffsetCondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import com.jiuqi.bi.dataset.restrict.RestrictionHelper;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.dataset.restrict.ValueListCondition;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class FilterExecutor {
    private BIDataSetImpl dataset;
    private int sys_timekeycol;
    private int sys_rownumcol;

    public FilterExecutor(BIDataSet dataset) {
        this.dataset = (BIDataSetImpl)dataset;
        this.sys_timekeycol = dataset.getMetadata().indexOf("SYS_TIMEKEY");
        this.sys_rownumcol = dataset.getMetadata().indexOf("SYS_ROWNUM");
    }

    public BIDataSetImpl filter(String filterExpr) throws BIDataSetException {
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, null);
        FilterItem filterItem = new FilterItem(null, filterExpr);
        try {
            List<ICondition> conds = this.transformFilterItemToCondition(filterItem);
            return this.doFilter(conds, dsCxt, true);
        }
        catch (SyntaxException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
    }

    public BIDataSetImpl filter(List<FilterItem> filterItems) throws BIDataSetException {
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, null);
        ArrayList<ICondition> conds = new ArrayList<ICondition>();
        try {
            for (FilterItem filter : filterItems) {
                List<ICondition> fic = this.transformFilterItemToCondition(filter);
                conds.addAll(fic);
            }
        }
        catch (SyntaxException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        return this.doFilter(conds, dsCxt, true);
    }

    public BIDataSetImpl filter(IDSFilter filter) throws BIDataSetException {
        ArrayList<Integer> rowIdxes = new ArrayList<Integer>();
        int count = this.dataset.getRecordCount();
        MemoryDataRow row = new MemoryDataRow();
        for (int i = 0; i < count; ++i) {
            row._setBuffer(this.dataset.getRowData(i));
            if (!filter.judge((DataRow)row)) continue;
            rowIdxes.add(i);
        }
        return FilterExecutor.createDataset(this.dataset, rowIdxes, false);
    }

    public BIDataSetImpl filter(List<IASTNode> nodes, DSFormulaContext context) throws BIDataSetException {
        if (context == null) {
            context = new DSFormulaContext(this.dataset);
        }
        ArrayList<ICondition> conds = new ArrayList<ICondition>();
        for (IASTNode node : nodes) {
            ICondition cond = this.transformASTNodeToCondition(node, context);
            conds.add(cond);
        }
        return this.doFilter(conds, context.clone(), false);
    }

    private void tryCalcField(List<IdxFilterInfo> idxFilters, List<IASTNode> exprFilters, DSFormulaContext context) throws BIDataSetException {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        if (!idxFilters.isEmpty()) {
            for (IdxFilterInfo idxFilter : idxFilters) {
                Column column = metadata.getColumn(idxFilter.colIdx);
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
                if (!info.isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC) continue;
                cols.add(column.getIndex());
            }
        }
        if (!exprFilters.isEmpty()) {
            for (IASTNode expr : exprFilters) {
                for (IASTNode node : expr) {
                    DSFieldNode dsNode;
                    BIDataSetFieldInfo info;
                    if (!(node instanceof DSFieldNode) || !(info = (dsNode = (DSFieldNode)node).getFieldInfo()).isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC) continue;
                    Column column = metadata.find(info.getName());
                    cols.add(column.getIndex());
                }
            }
        }
        if (cols.isEmpty()) {
            return;
        }
        int[] colIdxes = new int[cols.size()];
        for (int i = 0; i < colIdxes.length; ++i) {
            colIdxes[i] = (Integer)cols.get(i);
        }
        this.dataset.doCalcField(colIdxes);
    }

    private BIDataSetImpl doFilter(List<ICondition> conds, DSFormulaContext context, boolean sortForOriginal) throws BIDataSetException {
        conds = this.removeNotExistFieldCondtion(conds);
        ArrayList<IdxFilterInfo> idxFilters = new ArrayList<IdxFilterInfo>();
        ArrayList<IASTNode> exprFilters = new ArrayList<IASTNode>();
        this.classifyFilter(conds, idxFilters, exprFilters, context);
        this.tryCalcField(idxFilters, exprFilters, context);
        return FilterExecutor.doFilter(this.dataset, idxFilters, exprFilters, context, sortForOriginal);
    }

    private static boolean matchFirstRowData(BIDataSetImpl dataset, List<IdxFilterInfo> idxFilters, List<IASTNode> exprFilters, DSFormulaContext dsCxt) throws BIDataSetException {
        Object[] rowData = dataset.getRowData(0);
        if (!idxFilters.isEmpty()) {
            for (IdxFilterInfo filter : idxFilters) {
                int dataType = dataset.getMetadata().getColumn(filter.colIdx).getDataType();
                if (dataType == 5 && !(filter.value.get(0) instanceof Integer)) {
                    dataType = 3;
                }
                try {
                    if (FilterExecutor.matchDataInArray(dataType, rowData[filter.colIdx], filter.value)) continue;
                    return false;
                }
                catch (SyntaxException e) {
                    throw new BIDataSetException(e.getMessage(), e);
                }
            }
        }
        if (!exprFilters.isEmpty()) {
            DSFormulaContext context = dsCxt.clone();
            context.setCurRow(dataset.get(0));
            if (!FilterExecutor.match(exprFilters, context)) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchDataInArray(int dataType, Object dest, List<Object> list) throws SyntaxException {
        for (Object fv : list) {
            if (com.jiuqi.bi.syntax.DataType.compare((int)dataType, (Object)dest, (Object)fv) != 0) continue;
            return true;
        }
        return false;
    }

    public static BIDataSetImpl doFilter(BIDataSetImpl dataset, List<IdxFilterInfo> idxFilters, List<IASTNode> exprFilters, DSFormulaContext dsCxt, boolean sortForOriginal) throws BIDataSetException {
        int count = dataset.getRecordCount();
        if (count == 0) {
            return new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)new MemoryDataSet(dataset.getMetadata()));
        }
        if (count == 1) {
            if (FilterExecutor.matchFirstRowData(dataset, idxFilters, exprFilters, dsCxt)) {
                return dataset;
            }
            return new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)new MemoryDataSet(dataset.getMetadata()));
        }
        ArrayList<Integer> rowIdxes = new ArrayList();
        boolean needSort = true;
        if (idxFilters.size() <= 1) {
            needSort = false;
        }
        if (idxFilters.size() > 0) {
            List<Integer> subRows;
            DSIndex dsIdx = dataset.getDSIndex();
            if (FilterExecutor.fitCompoundIndex(dataset, idxFilters)) {
                int[] cols = new int[idxFilters.size()];
                Object[] data = new Object[idxFilters.size()];
                for (int i = 0; i < idxFilters.size(); ++i) {
                    cols[i] = idxFilters.get((int)i).colIdx;
                    data[i] = idxFilters.get((int)i).value.get(0);
                }
                if (!dsIdx.containCompoundIndex(cols)) {
                    dsIdx.buildCompoundIndex(cols);
                }
                subRows = dsIdx.search(cols, data);
                needSort = false;
            } else {
                subRows = dsIdx.doFilter(idxFilters);
            }
            if (exprFilters == null || exprFilters.isEmpty()) {
                rowIdxes = subRows;
            } else {
                DSFormulaContext context = dsCxt.clone();
                for (Integer row : subRows) {
                    context.setCurRow(dataset.get(row));
                    if (!FilterExecutor.match(exprFilters, context)) continue;
                    rowIdxes.add(row);
                }
            }
        } else {
            DSFormulaContext context = dsCxt.clone();
            for (int i = 0; i < count; ++i) {
                context.setCurRow(dataset.get(i));
                if (!FilterExecutor.match(exprFilters, context)) continue;
                rowIdxes.add(i);
            }
        }
        if (rowIdxes.size() == dataset.getRecordCount()) {
            return dataset;
        }
        return FilterExecutor.createDataset(dataset, rowIdxes, sortForOriginal && needSort);
    }

    private static boolean fitCompoundIndex(BIDataSet dataset, List<IdxFilterInfo> idxFilters) throws BIDataSetException {
        if (idxFilters.size() <= 1) {
            return false;
        }
        for (IdxFilterInfo idx : idxFilters) {
            if (idx.value.size() != 1) {
                return false;
            }
            if (FilterExecutor.isIndexSupport(dataset, idx.colIdx)) continue;
            return false;
        }
        return true;
    }

    private static boolean isIndexSupport(BIDataSet dataset, int col) {
        Column column = dataset.getMetadata().getColumn(col);
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
        if (info.getFieldType() == FieldType.MEASURE) {
            return false;
        }
        return info.getValType() == DataType.STRING.value() || info.getValType() == DataType.INTEGER.value() || info.getValType() == DataType.BOOLEAN.value();
    }

    private List<ICondition> removeNotExistFieldCondtion(List<ICondition> conds) {
        ArrayList<ICondition> newConds = new ArrayList<ICondition>();
        for (ICondition cond : conds) {
            if (cond.getCol() != -1) {
                newConds.add(cond);
                continue;
            }
            if (!(cond instanceof ExpressionCondition)) continue;
            newConds.add(cond);
        }
        return newConds;
    }

    private void classifyFilter(List<ICondition> conds, List<IdxFilterInfo> idxFilters, List<IASTNode> exprFilters, DSFormulaContext dsCxt) throws BIDataSetException {
        for (ICondition cond : conds) {
            if (cond.canUseIndex()) {
                List<Object> data;
                Object obj = cond.getValue(dsCxt);
                if (obj instanceof Object[]) {
                    data = Arrays.asList((Object[])obj);
                } else {
                    data = new ArrayList<Object>(1);
                    data.add(obj);
                }
                idxFilters.add(new IdxFilterInfo(cond.getCol(), data));
                continue;
            }
            if (cond instanceof AggrCondition) continue;
            Object value = cond.getValue(dsCxt);
            if (value instanceof IExpression) {
                IExpression expr = (IExpression)value;
                exprFilters.add(expr.getChild(0));
                continue;
            }
            if (value instanceof Object[]) {
                Object[] vlist = (Object[])value;
                Column column = this.dataset.getMetadata().getColumn(cond.getCol());
                DSFieldNode leftOperand = new DSFieldNode(null, (BIDataSetFieldInfo)column.getInfo());
                int dataType = DataType.translateToSyntaxType(column.getDataType());
                if (vlist.length == 0) continue;
                if (vlist.length == 1) {
                    DataNode rightOperand = new DataNode(null, dataType, vlist[0]);
                    Equal equal = new Equal(null, (IASTNode)leftOperand, (IASTNode)rightOperand);
                    exprFilters.add((IASTNode)equal);
                    continue;
                }
                ArrayData array = new ArrayData(dataType, Arrays.asList(vlist));
                DataNode rightOperand = new DataNode(null, array);
                In in = new In(null, (IASTNode)leftOperand, (IASTNode)rightOperand);
                exprFilters.add((IASTNode)in);
                continue;
            }
            throw new BIDataSetException("\u6682\u4e0d\u652f\u6301\u8fc7\u6ee4\u8868\u8fbe\u5f0f" + value);
        }
    }

    private static boolean match(List<IASTNode> nodes, DSFormulaContext context) throws BIDataSetException {
        boolean match = true;
        try {
            for (IASTNode node : nodes) {
                if (node.judge((IContext)context)) continue;
                match = false;
                break;
            }
        }
        catch (SyntaxException e) {
            throw new BIDataSetException("\u89e3\u6790\u516c\u5f0f\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        return match;
    }

    private List<ICondition> transformFilterItemToCondition(FilterItem filterItem) throws BIDataSetException, SyntaxException {
        Object cond;
        Column column;
        ArrayList<ICondition> conds = new ArrayList<ICondition>();
        if (filterItem.getKeyList() != null && filterItem.getKeyList().size() > 0 && filterItem.getFieldName() != null) {
            column = this.dataset.getMetadata().find(filterItem.getFieldName());
            if (column != null) {
                RestrictionDescriptor rstDesc = new RestrictionDescriptor(2, (BIDataSetFieldInfo)column.getInfo(), column.getIndex(), filterItem.getKeyList().toArray());
                cond = new ValueListCondition(this.dataset, rstDesc, this.sys_timekeycol, this.sys_rownumcol);
                conds.add((ICondition)cond);
            } else {
                throw new BIDataSetException("\u672a\u77e5\u7684\u6570\u636e\u96c6\u5b57\u6bb5\uff1a" + filterItem.getFieldName());
            }
        }
        if (StringUtils.isNotEmpty((String)filterItem.getExpr())) {
            DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
            DSFormulaContext context = new DSFormulaContext(this.dataset, null);
            cond = parser.parseCond(filterItem.getExpr(), context);
            IASTNode root = cond.getChild(0);
            ArrayList<RestrictionDescriptor> restricts = new ArrayList<RestrictionDescriptor>();
            FilterExecutor.analysisNode(restricts, root, context);
            for (RestrictionDescriptor rst : restricts) {
                conds.add(FilterExecutor.transform(this.dataset, rst));
            }
        }
        if (filterItem.getRange() != null) {
            column = this.dataset.getMetadata().find(filterItem.getFieldName());
            if (column == null) {
                throw new BIDataSetException("\u672a\u77e5\u7684\u6570\u636e\u96c6\u5b57\u6bb5\uff1a" + filterItem.getFieldName());
            }
            RangeValues range = filterItem.getRange();
            cond = this.createConditionByRangeRestrict(filterItem.getFieldName(), range);
            conds.add((ICondition)cond);
        }
        return conds;
    }

    private ICondition transformASTNodeToCondition(IASTNode node, DSFormulaContext context) throws BIDataSetException {
        RestrictionDescriptor desc = RestrictionHelper.checkRestriction(context, node);
        return FilterExecutor.transform(this.dataset, desc);
    }

    private ICondition createConditionByRangeRestrict(String fieldName, RangeValues range) throws SyntaxException {
        Column column = this.dataset.getMetadata().find(fieldName);
        if (range.max != null && range.min != null && range.max.equals(range.min)) {
            RestrictionDescriptor rstDesc = new RestrictionDescriptor(2, (BIDataSetFieldInfo)column.getInfo(), column.getIndex(), new Object[]{range.max});
            return new ValueListCondition(this.dataset, rstDesc, this.sys_timekeycol, this.sys_rownumcol);
        }
        StringBuffer exprBuf = new StringBuffer();
        if (range.min != null) {
            if (exprBuf.length() > 0) {
                exprBuf.append(" and ");
            }
            exprBuf.append(fieldName).append(">=");
            exprBuf.append(this.transformValueToExpr(range.min));
        }
        if (range.max != null) {
            if (exprBuf.length() > 0) {
                exprBuf.append(" and ");
            }
            exprBuf.append(fieldName).append("<=");
            exprBuf.append(this.transformValueToExpr(range.max));
        }
        DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
        DSFormulaContext context = new DSFormulaContext(this.dataset, null);
        DSExpression expr = parser.parseCond(exprBuf.toString(), context);
        RestrictionDescriptor rstDesc = new RestrictionDescriptor(3, (BIDataSetFieldInfo)column.getInfo(), column.getIndex(), (Object)expr);
        return new ExpressionCondition(rstDesc);
    }

    static void analysisNode(List<RestrictionDescriptor> restricts, IASTNode node, DSFormulaContext context) throws BIDataSetException {
        if (FilterExecutor.isNodeCanSeparate(node)) {
            for (int i = 0; i < node.childrenSize(); ++i) {
                FilterExecutor.analysisNode(restricts, node.getChild(i), context);
            }
        } else {
            RestrictionDescriptor desc = RestrictionHelper.checkRestriction(context, node);
            restricts.add(desc);
        }
    }

    private static boolean isNodeCanSeparate(IASTNode node) {
        FunctionNode fn;
        if (node instanceof And) {
            return true;
        }
        return node instanceof FunctionNode && (fn = (FunctionNode)node).getDefine().name().equalsIgnoreCase("AND");
    }

    static ICondition transform(BIDataSetImpl dataset, RestrictionDescriptor rstDesc) throws BIDataSetException {
        if (rstDesc.mode == 2) {
            return new ValueListCondition(dataset, rstDesc);
        }
        if (rstDesc.mode == 1) {
            ArrayList<RestrictionDescriptor> offsetRst = new ArrayList<RestrictionDescriptor>(1);
            offsetRst.add(rstDesc);
            return new OffsetCondition(dataset, offsetRst, true);
        }
        if (rstDesc.mode == 0) {
            String tag = (String)rstDesc.condition;
            if (RestrictionTag.isCURRENT(tag)) {
                return new ValueListCondition(dataset, rstDesc);
            }
            if (RestrictionTag.isALL(tag) || RestrictionTag.isMB(tag)) {
                return new AggrCondition(rstDesc);
            }
            throw new BIDataSetException("\u672a\u652f\u6301\u7684\u9650\u5b9a\u5173\u952e\u5b57\uff1a" + tag);
        }
        return new ExpressionCondition(rstDesc);
    }

    private String transformValueToExpr(Object value) {
        if (value instanceof Calendar) {
            Calendar calendar = (Calendar)value;
            StringBuffer buf = new StringBuffer();
            buf.append("DATE(").append(calendar.get(1)).append(", ");
            buf.append(calendar.get(2) + 1).append(", ");
            buf.append(calendar.get(5)).append(")");
            return buf.toString();
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }

    private static BIDataSetImpl createDataset(final BIDataSetImpl dataset, List<Integer> rowIdx, boolean sortForOriginal) {
        if (sortForOriginal && rowIdx.size() > 1) {
            Collections.sort(rowIdx, new Comparator<Integer>(){

                @Override
                public int compare(Integer o1, Integer o2) {
                    BIDataRow row1 = dataset.get(o1);
                    BIDataRow row2 = dataset.get(o2);
                    return row1.getRowNum() - row2.getRowNum();
                }
            });
        }
        int[] rows = new int[rowIdx.size()];
        for (int i = 0; i < rowIdx.size(); ++i) {
            rows[i] = rowIdx.get(i);
        }
        return new BIDataSetImpl(dataset, rows);
    }
}

