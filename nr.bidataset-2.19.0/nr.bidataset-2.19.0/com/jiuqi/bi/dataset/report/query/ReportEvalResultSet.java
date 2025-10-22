/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSetError
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 */
package com.jiuqi.bi.dataset.report.query;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetError;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.report.query.ReportQueryContext;
import com.jiuqi.bi.dataset.report.query.network.EvalExprItem;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo;
import com.jiuqi.bi.dataset.report.tree.SortedUnitTree;
import com.jiuqi.bi.dataset.report.tree.SortedUnitTreeInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ReportEvalResultSet {
    private List<Object[]> rows = new ArrayList<Object[]>();
    private List<EvalExprItem> evalItems = new ArrayList<EvalExprItem>();
    private EvalExprItem conditionEvalItem;
    private static int MAX_DATA_SIZE = ReportEvalResultSet.getMaxDataSize();

    public int size() {
        return this.rows.size();
    }

    public void toResult(MemoryDataSet<BIDataSetFieldInfo> memoryDataSet, int pageSize, int currentPage) throws BIDataSetException {
        try {
            int beginIndex = 0;
            int endIndex = this.size();
            if (pageSize > 0 && (endIndex = (beginIndex = currentPage * pageSize) + pageSize) > this.size()) {
                endIndex = this.size();
            }
            if (endIndex > beginIndex) {
                for (int r = beginIndex; r < endIndex; ++r) {
                    Object[] rowDatas = this.rows.get(r);
                    DataRow dataRow = memoryDataSet.add();
                    for (int c = 0; c < rowDatas.length; ++c) {
                        Object value = rowDatas[c];
                        dataRow.setValue(c, value);
                    }
                    dataRow.commit();
                }
            }
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
    }

    public void toPreviewResult(PreviewResultVo resultVo, int pageSize, int currentPage) throws DataSetException {
        int beginIndex = 0;
        int endIndex = this.size();
        if (pageSize > 0 && (endIndex = (beginIndex = currentPage * pageSize) + pageSize) > this.size()) {
            endIndex = this.size();
        }
        if (endIndex > beginIndex) {
            resultVo.setResult(this.rows.subList(beginIndex, endIndex));
            resultVo.setTotalCount(this.size());
        }
    }

    public void executeEval(ReportQueryContext context) throws SyntaxException {
        this.checkDataSetSize();
        if (this.conditionEvalItem == null || this.conditionEvalItem.judge(context)) {
            Object[] rowDatas = new Object[this.evalItems.size()];
            for (EvalExprItem evalItem : this.evalItems) {
                try {
                    Object evalValue = evalItem.eval(context);
                    if (evalItem.getDataType() == 10) {
                        evalValue = DataTypesConvert.toBigDecimal((Object)evalValue);
                    }
                    rowDatas[evalItem.getResultIndex()] = evalValue;
                }
                catch (SyntaxException e) {
                    if (e.isCanIgnore()) continue;
                    context.getMonitor().exception((Exception)((Object)e));
                }
            }
            if (rowDatas[context.getDsModel().getUnitKeyIndex()] == null) {
                return;
            }
            SortedUnitTree sortedUnitTree = context.getSortedUnitTree();
            if (context.needResetParentAndOder()) {
                SortedUnitTreeInfo info = sortedUnitTree.getUnitTreeInfo(rowDatas);
                if (info.getParents() == null) {
                    sortedUnitTree.resetOrder(rowDatas);
                    this.rows.add(rowDatas);
                } else {
                    List<Object[]> treeRows = sortedUnitTree.getUnitTreeRows(info, rowDatas);
                    this.rows.addAll(treeRows);
                }
            } else {
                if (context.needResetOrder()) {
                    sortedUnitTree.resetOrder(rowDatas);
                }
                this.rows.add(rowDatas);
            }
        }
    }

    private void checkDataSetSize() {
        if (MAX_DATA_SIZE > 0 && this.rows.size() >= MAX_DATA_SIZE) {
            throw new DataSetError("\u5185\u5b58\u6570\u636e\u96c6\u5927\u5c0f\u8d85\u51fa\u4e86\u7cfb\u7edf\u8d1f\u8f7d\u9650\u5236(" + MAX_DATA_SIZE + ")\uff0c\u8bf7\u51cf\u5c11\u67e5\u8be2\u6570\u636e\u91cf\u3002");
        }
    }

    private static int getMaxDataSize() {
        String maxSize = System.getProperty("bi.maxDataSize");
        if (!StringUtils.isEmpty((String)maxSize)) {
            try {
                return Integer.parseInt(maxSize);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return 1000000;
    }

    public void sort(final List<Integer> sortFieldIndexes) {
        if (sortFieldIndexes.isEmpty()) {
            return;
        }
        Comparator<Object[]> rowComparator = new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                Iterator iterator = sortFieldIndexes.iterator();
                while (iterator.hasNext()) {
                    Object value2;
                    int index = (Integer)iterator.next();
                    Object value1 = o1[index];
                    if (value1 == (value2 = o2[index])) continue;
                    if (value1 == null) {
                        return -1;
                    }
                    if (value2 == null) {
                        return 1;
                    }
                    int c = ((Comparable)value1).compareTo(value2);
                    if (c == 0) continue;
                    return c;
                }
                return 0;
            }
        };
        Collections.sort(this.rows, rowComparator);
    }

    public int getColumnCount() {
        return this.evalItems.size();
    }

    public void addEvalExprItem(EvalExprItem item) {
        this.evalItems.add(item);
    }

    public void setCondition(EvalExprItem conditionEvalItem) {
        this.conditionEvalItem = conditionEvalItem;
    }
}

