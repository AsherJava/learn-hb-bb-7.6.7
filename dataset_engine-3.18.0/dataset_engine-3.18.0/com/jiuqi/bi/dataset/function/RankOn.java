/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.keyword.KeywordNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;
import com.jiuqi.bi.dataset.restrict.RestrictionHelper;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.syntax.DataType;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RankOn
extends DSFunction {
    private static final long serialVersionUID = 295227785195373077L;

    public RankOn() {
        this.parameters().add(new Parameter("expr", 0, "\u5ea6\u91cf\u5b57\u6bb5\u6216\u8ba1\u7b97\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("sortType", 0, "\u6392\u5e8f\u7c7b\u578b\uff1aASC\u6216DESC"));
        this.parameters().add(new Parameter("restrict", 1, "\u9650\u5b9a\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        BIDataRow curRow;
        DSFormulaContext dsCxt = (DSFormulaContext)context;
        BIDataSetImpl dataset = dsCxt.getDataSet();
        if (parameters.size() >= 3) {
            List<Integer> currRestCols;
            DSFormulaContext cxt = dsCxt.clone();
            List<IASTNode> exprFilters = parameters.subList(2, parameters.size());
            try {
                currRestCols = this.findCurrentRowValueCondCols(exprFilters, cxt);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
            ArrayList<Integer> rowIdxes = new ArrayList<Integer>();
            int count = dataset.getRecordCount();
            for (int i = 0; i < count; ++i) {
                cxt.setCurRow(dataset.get(i));
                if (!this.match(exprFilters, currRestCols, cxt, dsCxt.getCurRow())) continue;
                rowIdxes.add(i);
            }
            int[] rows = new int[rowIdxes.size()];
            for (int i = 0; i < rowIdxes.size(); ++i) {
                rows[i] = (Integer)rowIdxes.get(i);
            }
            dataset = new BIDataSetImpl(dataset, rows);
        }
        if ((curRow = dsCxt.getCurRow()) == null) {
            throw new SyntaxException("\u7f3a\u5c11\u5f53\u524d\u884c\u4fe1\u606f\uff0c\u65e0\u6cd5\u6267\u884crankon");
        }
        Object curMsValue = null;
        String expr = parameters.get(0).interpret(context, Language.FORMULA, null);
        int colIdx = dataset.getMetadata().indexOf(expr);
        if (colIdx == -1) {
            int row = this.getRow(dataset, curRow);
            if (row == -1) {
                return null;
            }
            try {
                HashMap<String, String> calcFieldMap = new HashMap<String, String>();
                calcFieldMap.put(expr, expr);
                dataset = (BIDataSetImpl)dataset.addCalcFields(calcFieldMap);
                colIdx = dataset.getMetadata().indexOf(expr);
                curMsValue = dataset.get(row).getValue(colIdx);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException((Throwable)e);
            }
        } else {
            curMsValue = curRow.getValue(colIdx);
        }
        int sortType = this.getSortType(parameters.get(1));
        dataset = (BIDataSetImpl)dataset.sort(colIdx, sortType);
        Iterator<BIDataRow> dsItor = dataset.iterator();
        Object lastVal = null;
        int pos = 1;
        while (dsItor.hasNext()) {
            BIDataRow dataRow = dsItor.next();
            Object val = dataRow.getValue(colIdx);
            int compareTo = DataType.compareObject((Object)curMsValue, (Object)val);
            if (compareTo == 0) {
                return pos;
            }
            if (DataType.compareObject(lastVal, (Object)val) != 0) {
                lastVal = val;
                ++pos;
                continue;
            }
            if (this.unbrokenRank()) continue;
            ++pos;
        }
        return null;
    }

    boolean unbrokenRank() {
        return false;
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        return this.evalute(context, paramNodes);
    }

    @Override
    public boolean isNeedOptimize() {
        return true;
    }

    public String name() {
        return "DS_RANKON";
    }

    public String title() {
        return "\u83b7\u53d6\u9650\u5b9a\u8868\u8fbe\u5f0f\u9650\u5b9a\u540e\u7684\u6570\u636e\u96c6\u5728\u539f\u59cb\u6570\u636e\u96c6\u4e2d\uff0c\u6307\u5b9a\u5ea6\u91cf\u7684\u6392\u540d\uff0c\u6392\u540d\u503c\u4e0d\u8fde\u7eed";
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

    private int getRow(BIDataSetImpl dataset, BIDataRow dataRow) {
        List columns = dataset.getMetadata().getColumns();
        ArrayList<Integer> dimCols = new ArrayList<Integer>();
        ArrayList<Object> dimDatas = new ArrayList<Object>();
        for (Column column : columns) {
            if (!((BIDataSetFieldInfo)column.getInfo()).isDimention()) continue;
            dimCols.add(column.getIndex());
            dimDatas.add(dataRow.getValue(column.getIndex()));
        }
        int[] colIdxes = new int[dimCols.size()];
        for (int i = 0; i < colIdxes.length; ++i) {
            colIdxes[i] = (Integer)dimCols.get(i);
        }
        int[] rs = dataset.find(colIdxes, dimDatas.toArray());
        if (rs.length > 0) {
            return rs[0];
        }
        return -1;
    }

    private List<Integer> findCurrentRowValueCondCols(List<IASTNode> filters, DSFormulaContext context) throws BIDataSetException {
        Metadata<BIDataSetFieldInfo> metadata = context.getDataSet().getMetadata();
        HashSet<Integer> hasAppear = new HashSet<Integer>();
        boolean hasRestTimekey = false;
        for (IASTNode node : filters) {
            String tag;
            boolean isCurrCond = false;
            RestrictionDescriptor desc = RestrictionHelper.checkRestriction(context, node);
            if (desc.mode == 0 && (RestrictionTag.isCURRENT(tag = (String)desc.condition) || RestrictionTag.isCURPERIOD(tag))) {
                isCurrCond = true;
            }
            if (isCurrCond) continue;
            for (IASTNode cur : node) {
                if (!(cur instanceof DSFieldNode)) continue;
                DSFieldNode dnode = (DSFieldNode)cur;
                String name = dnode.getName();
                hasAppear.add(metadata.indexOf(name));
                if (!dnode.getFieldInfo().isTimekey()) continue;
                hasRestTimekey = true;
            }
        }
        ArrayList<Integer> needRests = new ArrayList<Integer>();
        for (int i = 0; i < metadata.size(); ++i) {
            int npos;
            Column column;
            BIDataSetFieldInfo info;
            if (hasAppear.contains(i) || (info = (BIDataSetFieldInfo)(column = metadata.getColumn(i)).getInfo()).getName().equals("SYS_TIMEKEY") || hasRestTimekey && info.getFieldType() == FieldType.TIME_DIM || !info.isDimention() || !info.getName().equalsIgnoreCase(info.getKeyField()) || hasAppear.contains(npos = metadata.indexOf(info.getNameField()))) continue;
            needRests.add(column.getIndex());
        }
        List hiers = (List)metadata.getProperties().get("HIERARCHY");
        if (hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                String name = hier.getParentFieldName();
                Integer pos = metadata.indexOf(name);
                needRests.remove(pos);
            }
        }
        return needRests;
    }

    private boolean match(List<IASTNode> filters, List<Integer> currRestCols, DSFormulaContext context, BIDataRow calcRow) throws SyntaxException {
        boolean match = true;
        if (calcRow != null && context.getCurRow() != null) {
            for (Integer col : currRestCols) {
                Object curv;
                Object v = calcRow.getValue(col);
                int rs = DataType.compareObject((Object)v, (Object)(curv = context.getCurRow().getValue(col)));
                if (rs == 0) continue;
                match = false;
                break;
            }
        }
        if (match) {
            int size = filters.size();
            for (int i = 0; i < size; ++i) {
                IASTNode node = filters.get(i);
                try {
                    RestrictionDescriptor desc = RestrictionHelper.checkRestriction(context, node);
                    if (desc.mode == 0 || node.judge((IContext)context)) continue;
                    match = false;
                    break;
                }
                catch (BIDataSetException e) {
                    throw new SyntaxException((Throwable)e);
                }
            }
        }
        return match;
    }
}

