/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.executor;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SortExecutor {
    private BIDataSetImpl dataset;

    public SortExecutor(BIDataSetImpl dataset) {
        this.dataset = dataset;
    }

    public BIDataSetImpl sort(List<SortItem> sortItems) {
        ArrayList<Integer> cols = new ArrayList<Integer>();
        for (SortItem item : sortItems) {
            Column column = this.dataset.getMetadata().find(item.getFieldName());
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (!info.isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC) continue;
            cols.add(column.getIndex());
        }
        this.tryCalcField(cols);
        List<Integer> newRowList = this.sortByItems(sortItems);
        int[] sortedRow = new int[newRowList.size()];
        for (int i = 0; i < newRowList.size(); ++i) {
            sortedRow[i] = newRowList.get(i);
        }
        return new BIDataSetImpl(this.dataset, sortedRow);
    }

    public BIDataSetImpl sort(int colIdx, int sortType) {
        try {
            this.dataset.doCalcField(new int[]{colIdx});
        }
        catch (BIDataSetException e) {
            this.dataset.getOrDefaultLogger().error("\u6392\u5e8f\u65f6\u51fa\u9519\uff0c" + e.getMessage());
        }
        List<Integer> newRowList = this.sortByCols(new int[]{colIdx}, new int[]{sortType});
        int[] sortedRow = new int[newRowList.size()];
        for (int i = 0; i < newRowList.size(); ++i) {
            sortedRow[i] = newRowList.get(i);
        }
        return new BIDataSetImpl(this.dataset, sortedRow);
    }

    public BIDataSetImpl sort(Comparator<BIDataRow> comparator) {
        int rowCount = this.dataset.getRecordCount();
        ArrayList<BIDataRow> rowList = new ArrayList<BIDataRow>(rowCount);
        for (int i = 0; i < rowCount; ++i) {
            rowList.add(this.dataset.get(i));
        }
        Collections.sort(rowList, comparator);
        int[] sortedRow = new int[rowList.size()];
        for (int i = 0; i < rowList.size(); ++i) {
            sortedRow[i] = ((BIDataRow)rowList.get(i)).getRowNum();
        }
        return new BIDataSetImpl(this.dataset, sortedRow);
    }

    public List<Integer> sortByItems(List<SortItem> sortItems) {
        int[] sortColIdxes = new int[sortItems.size()];
        int[] sortColTypes = new int[sortItems.size()];
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        for (int i = 0; i < sortItems.size(); ++i) {
            SortItem item = sortItems.get(i);
            sortColIdxes[i] = metadata.indexOf(item.getFieldName());
            sortColTypes[i] = item.getSortType();
        }
        return this.sortByCols(sortColIdxes, sortColTypes);
    }

    public List<Integer> sortByExpr(String expr, int sortType) throws BIDataSetException {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        int colIdx = metadata.indexOf(expr);
        if (colIdx > 0) {
            return this.sortByCols(new int[]{colIdx}, new int[]{sortType});
        }
        try {
            return this.sortByFormula(expr, sortType);
        }
        catch (SyntaxException e) {
            throw new BIDataSetException("\u6570\u636e\u96c6\u6267\u884c\u6392\u5e8f\u65f6\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    private List<Integer> sortByCols(int[] sortColIdxes, int[] sortColTypes) {
        int rowCount = this.dataset.getRecordCount();
        ArrayList<Integer> rowIndexList = new ArrayList<Integer>(rowCount);
        for (int i = 0; i < rowCount; ++i) {
            rowIndexList.add(i);
        }
        DataRowComparator comparator = new DataRowComparator(this.dataset, sortColIdxes, sortColTypes);
        Collections.sort(rowIndexList, comparator);
        return rowIndexList;
    }

    private List<Integer> sortByFormula(String expr, int sortType) throws SyntaxException {
        int rowCount = this.dataset.getRecordCount();
        DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, null);
        DSExpression evalExpr = parser.parseEval(expr, dsCxt);
        ArrayList<Integer> rowIndexList = new ArrayList<Integer>(rowCount);
        for (int i = 0; i < rowCount; ++i) {
            rowIndexList.add(i);
        }
        ExprComparator comparator = new ExprComparator(this.dataset, evalExpr, sortType);
        Collections.sort(rowIndexList, comparator);
        return rowIndexList;
    }

    private void tryCalcField(List<Integer> cols) {
        try {
            if (cols.size() > 0) {
                int[] colIdxes = new int[cols.size()];
                for (int i = 0; i < colIdxes.length; ++i) {
                    colIdxes[i] = cols.get(i);
                }
                this.dataset.doCalcField(colIdxes);
            }
        }
        catch (BIDataSetException e) {
            this.dataset.getOrDefaultLogger().error("\u6392\u5e8f\u65f6\u51fa\u9519\uff0c" + e.getMessage());
        }
    }

    private final class ExprComparator
    implements Comparator<Integer> {
        private BIDataSetImpl dataset;
        private IExpression expr;
        private int sortType;

        public ExprComparator(BIDataSetImpl dataset, IExpression expr, int sortType) {
            this.dataset = dataset;
            this.expr = expr;
            this.sortType = sortType;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            try {
                Object d1 = this.expr.evaluate((IContext)new DSFormulaContext(this.dataset, this.dataset.get(o1)));
                Object d2 = this.expr.evaluate((IContext)new DSFormulaContext(this.dataset, this.dataset.get(o2)));
                int compareTo = DataType.compareObject((Object)d1, (Object)d2);
                if (compareTo != 0) {
                    return compareTo * this.sortType;
                }
                return o1.compareTo(o2);
            }
            catch (SyntaxException e) {
                this.dataset.getOrDefaultLogger().error("\u6267\u884c\u6392\u5e8f\u65f6\u51fa\u73b0\u9519\u8bef\uff0c" + e.getMessage());
                return 0;
            }
        }
    }

    private final class DataRowComparator
    implements Comparator<Integer> {
        private BIDataSetImpl dataset;
        private int[] sortColIdxes;
        private int[] sortColTypes;

        public DataRowComparator(BIDataSetImpl dataset, int[] sortColIdxes, int[] sortColTypes) {
            this.dataset = dataset;
            this.sortColIdxes = sortColIdxes;
            this.sortColTypes = sortColTypes;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            Object[] row1 = this.dataset.getRowData(o1);
            Object[] row2 = this.dataset.getRowData(o2);
            for (int i = 0; i < this.sortColIdxes.length; ++i) {
                Object d1 = row1[this.sortColIdxes[i]];
                Object d2 = row2[this.sortColIdxes[i]];
                int sortType = this.sortColTypes[i];
                int compareTo = DataType.compareObject((Object)d1, (Object)d2);
                if (compareTo == 0) continue;
                return compareTo * sortType;
            }
            return o1.compareTo(o2);
        }
    }
}

